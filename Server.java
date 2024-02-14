import Stocks.Stock;
import Users.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("Server Started!");
        ServerSocket server = new ServerSocket(3000);

        HashMap<SocketWrapper,User> hashMapOfClients = new HashMap<>();

        List<Stock> allStockList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        Stock stock1 = new Stock( "Stock1",2,40.0);
        Stock stock2 = new Stock( "Stock2",3,80.0);
        Stock stock3 = new Stock( "Stock3",3,30.0);
        Stock stock4 = new Stock( "Stock4",5,110.0);
        allStockList.add(stock1);
        allStockList.add(stock2);
        allStockList.add(stock3);
        allStockList.add(stock4);

        User user1 = new User("User1",allStockList);
        User user2 = new User("User2",allStockList);
        User user3 = new User("User3",allStockList);
        User user4 = new User("User4",allStockList);
        User user5 = new User("User5",allStockList);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);

        Scanner scanner = new Scanner(System.in);

        new Thread(()-> {
            String notification = "";
            while (true) {
                String newStr = scanner.nextLine();
                String[] out = newStr.split(" ");
                if (out[0].equalsIgnoreCase("I")
                        || out[0].equalsIgnoreCase("D")
                        ||out[0].equalsIgnoreCase("C")) {
                    Stock foundStock = null;
                    for (Stock stock : allStockList) {
                        if (stock.getName().equalsIgnoreCase(out[1])) {
                            foundStock = stock;
                            break;
                        }
                    }
                    if (out[0].equalsIgnoreCase("I")) {
                        if (foundStock != null) {
                            notification = foundStock.increasePrice(Double.parseDouble(out[2]));

                        }
                    } else if (out[0].equalsIgnoreCase("D")) {

                        if (foundStock != null) {
                            notification = foundStock.decreasePrice(Double.parseDouble(out[2]));

                        }
                    } else if (out[0].equalsIgnoreCase("C")) {

                        if (foundStock != null) {
                            notification = foundStock.changeCount(Integer.parseInt(out[2]));

                        }
                    }
                        try {
                            for( SocketWrapper client : hashMapOfClients.keySet()) {
                                User currUser = hashMapOfClients.get(client);
                                for(Stock userStock :currUser.getOwnStockList()){
                                        if(userStock.getName().equalsIgnoreCase(foundStock.getName())){
                                            client.write(new DataWrapper("Change", "", notification, ""));
                                            break;
                                        }
                                }
                                currUser.clearNotificationList();

                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                }
            }

            }).start();
        while (true){
            Socket clientSocket=server.accept();
            SocketWrapper client=new SocketWrapper(clientSocket);
            System.out.println("Client connected");
            new Thread(()->{
                    User currentUser=null;
                    try {

                        while (true) {


                            Object data = client.read();
                            String clientData = (String) data;
                            String[] out = clientData.split(" ");
                            if (out[0].equalsIgnoreCase("Login")) {
                                for (User user : userList) {
                                    if (user.getName().equalsIgnoreCase(out[1])) {

                                        hashMapOfClients.put(client,user);
                                        currentUser = user;
                                        System.out.println(currentUser.getName() + " logged in.");
                                        client.write(new DataWrapper("Login", "", currentUser.printNotfication(), currentUser.viewAllStocks()));
                                        break;
                                    }
                                }
                            }
                            if (out[0].equalsIgnoreCase("V")) {
                                client.write(new DataWrapper("ViewAll", "", "", currentUser.viewOwnStocks()));
                            }
                            if (out[0].equalsIgnoreCase("S")) {
                                currentUser.subscribe(out[1]);
                            }
                            if (out[0].equalsIgnoreCase("U")) {
                                currentUser.unSubscribe(out[1]);
                                // client.write(new DataWrapper("Unsubscribe", "","",""));
                            }


                        }
                    }
                        catch (IOException e){
                            System.out.println(currentUser.getName() + " disconnected");
                            //  throw new RuntimeException(e);
                        }
                         catch (ClassNotFoundException e) {
                           throw new RuntimeException(e);
                        }
                        finally {
                            try {
                                hashMapOfClients.remove(client);
                                client.closeConnection();
                             //   listOfClients.remove(client);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }


            }).start();


        }

    }
}
