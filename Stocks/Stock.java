package Stocks;

import Users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stock implements Serializable {

 private List<User> userList;
 private String name;
 private int count;
 private double stockPrice;

 public Stock(String name , int count , double stockPrice){
     this.count = count;
     this.stockPrice = stockPrice;
     this.name = name;
     userList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public void subscribe(User user){
        userList.add(user);
    }

    public void unsubscirbe(User user){
        userList.remove(user);
    }

    public String increasePrice(double price){
        this.setStockPrice(this.getStockPrice() + price);
        String notification = "New notifications - Stock price increased !  Stock name : " + this.getName() + "  Stock count : " + this.getCount() + "  Stock price : "+this.getStockPrice();
        notifyAllUser(notification);
        return notification;
    }

    public String decreasePrice (double price){
        this.setStockPrice(this.getStockPrice() - price);
        String notification = "New notifications -  Stock price decreased !  Stock name : " + this.getName() + "  Stock count : " + this.getCount() + "  Stock price : "+this.getStockPrice();
        notifyAllUser(notification);
        return notification;
    }

    public String changeCount(int newCount){
        this.setCount(newCount);
        String notification ="New notifications -  Stock count changed !  Stock name :  " + this.getName() + "  Stock count : " + this.getCount() + "  Stock price : "+this.getStockPrice();
        notifyAllUser(notification);
        return notification;
    }

    public void notifyAllUser(String notification){
        for(User user : userList){
            user.notify(notification);
        }
    }

    public String stockInfoPrint(){
        String newStr = this.getName() + " " + this.getCount() + " "+this.getStockPrice();
        return newStr;
    }

}
