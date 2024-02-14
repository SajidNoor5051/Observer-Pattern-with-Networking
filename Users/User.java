package Users;

import Stocks.Stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable  {

    List<Stock> ownStockList;
    List<Stock> globalStockList;
    List<String> notificationList;
    String name;

    public List<String> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<String> notificationList) {
        this.notificationList = notificationList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name , List<Stock> globalStockList){
        this.name = name;
        this.ownStockList = new ArrayList<>();
        this.notificationList = new ArrayList<>();
        this.globalStockList = globalStockList;
    }


    public void subscribe(String name){

        for (Stock stock : globalStockList){
            if(stock.getName().equalsIgnoreCase(name)){
                stock.subscribe(this);
                ownStockList.add(stock);
                break;
            }
        }

    }

    public void unSubscribe(String name){
        for (Stock stock : globalStockList){
            if(stock.getName().equalsIgnoreCase(name)){
                stock.unsubscirbe(this);
                ownStockList.remove(stock);
                break;
            }
        }
    }

    public List<Stock> getOwnStockList() {
        return ownStockList;
    }

    public void setOwnStockList(List<Stock> ownStockList) {
        this.ownStockList = ownStockList;
    }

    public List<Stock> getGlobalStockList() {
        return globalStockList;
    }

    public void setGlobalStockList(List<Stock> globalStockList) {
        this.globalStockList = globalStockList;
    }


    public String viewOwnStocks(){
        String string =  "";
        for( Stock stock : ownStockList){
            string  += stock.stockInfoPrint()+ "\n";
        }
        return  string;
    }

    public String viewAllStocks(){
        String viewALlString ="";
        for( Stock stock : globalStockList){
           viewALlString += stock.stockInfoPrint()+"\n";
        }
        return viewALlString;
    }

    public void update(){
        System.out.println(this.getName() + " getting info of update!");
    }
    public void notify(String notification){
        this.notificationList.add(notification);
    }
    public String printNotfication(){
        String allNotification = "";
        for(String notification : notificationList){
            allNotification += notification+"\n";
        }
        clearNotificationList();
        return allNotification;
    }
    public void clearNotificationList(){
        notificationList.clear();
    }

}
