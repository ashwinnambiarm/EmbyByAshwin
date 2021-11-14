package com.ashwin.embybyashwin.Fragment.Login.Model;

public class User {
    private String userName;

    public User(String username){
        userName = username;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username){
        userName = username;
    }
}
