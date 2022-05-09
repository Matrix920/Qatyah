package com.example.money.database;

public class GroupUser {
    public static final String  GROUP_USERS = "GroupUsers";

    public int ID,GroupID,UserID;

    public GroupUser(int ID, int groupID, int userID) {
        this.ID = ID;
        GroupID = groupID;
        UserID = userID;
    }
}
