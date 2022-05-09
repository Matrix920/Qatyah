package com.example.money.database;

import android.content.Context;

public class Request {

    public static final String REQUESTS="Requests";

    public static final String REQUEST_ID="RequestID";
    public static final String RESPONSE="Response";

    public int RequestID,UserID,GroupID;
    public Boolean Response;

    public Request(int requestID, int groupID, int userID, Boolean response) {
        RequestID = requestID;
        UserID = userID;
        GroupID = groupID;
        Response = response;
    }

    public void acceptRequest(Context context){
        DBHelper db=new DBHelper(context);
        Response=true;
        db.updateRequest(this);

//        add money to group wallet
        Group group=db.getGroup(GroupID);

//        ammount to remove
        int amount=group.getRequestAmount(context);

        group.Wallet+=amount;
        db.updateGroup(group);

//        remove money from user budget
        User user=db.getUser(UserID);
        user.Budget-=amount;
        db.updateUser(user);
    }
}
