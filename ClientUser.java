import Stocks.Stock;
import Users.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientUser implements Serializable {
    public static void main(String[] args) throws IOException {
        //System.out.println("Hello world!");

        

       SocketWrapper server = new SocketWrapper("127.0.0.1",3000);
        Scanner scanner = new Scanner(System.in);

        new Thread(()->{


            while (true) {
                try {

                    Object data = server.read();
                    DataWrapper dataWrapper = (DataWrapper)data;
                    if(dataWrapper.getAction().equalsIgnoreCase("Login")){

                        System.out.println(dataWrapper.getAllStockInfo());
                        System.out.println(dataWrapper.getNotification());
                    }
                    else if(dataWrapper.getAction().equalsIgnoreCase("ViewAll")){
                        System.out.println(dataWrapper.getAllStockInfo());
                    }
                    else if(dataWrapper.getAction().equalsIgnoreCase("Change")){
                        System.out.println(dataWrapper.getNotification());
                    }

                } catch (IOException e) {
                    //  System.out.println("Connection Interrupted");
                    //  throw new RuntimeException(e);

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }



            }

        }).start();

        while (true) {
            String newStr = scanner.nextLine();
            //String[] out = newStr.split(" ");
            server.write(newStr);

        }


    }
}