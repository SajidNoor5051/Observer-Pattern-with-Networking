import Users.User;

import java.io.Serializable;
import java.security.PrivateKey;

public class DataWrapper implements Serializable {
    private String action;

    public String getAllStockInfo() {
        return allStockInfo;
    }

    public void setAllStockInfo(String allStockInfo) {
        this.allStockInfo = allStockInfo;
    }

    // private User user;
    private String userOrStockName;
    private String allStockInfo;
    private String notification;

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getUserOrStockName() {
        return userOrStockName;
    }

    public void setUserOrStockName(String userOrStockName) {
        this.userOrStockName = userOrStockName;
    }

    public DataWrapper( String action, String userOrStockName,String notification,String allStockInfo){

        this.action = action;
        this.userOrStockName = userOrStockName;
        this.notification =notification;
        this.allStockInfo = allStockInfo;

    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }





}
