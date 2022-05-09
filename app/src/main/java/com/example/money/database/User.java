package com.example.money.database;

public class User {

    public static final String USERS="Users";

    public static final String USER_ID="UserID";
    public static final   String USERNAME="Username";
    public static final   String PASSWORD="Password";
    public static final String   BUDGET="Budget";

    public int UserID;
    public String Username;
    public String Password;
    public int Budget;

    public User(int userID, String username, String password, int budget) {
        UserID = userID;
        Username = username;
        Password = password;
        Budget = budget;
    }
}
