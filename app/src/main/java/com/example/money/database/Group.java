package com.example.money.database;

import android.content.Context;

import java.util.List;

public class Group {

    public static final String GROUPS="Groups";

    public static final String  GROURP_ID="GroupID";
    public static final String  GROUP_NAME="GroupName";
    public static final String  WALLET="Wallet";
    public static final String  AMOUNT="Amount";

    public int GroupID;
    public int UserID;
    public int Wallet;
    public int Amount;
    public String GroupName;

    public Group(int groupID,String groupName, int userID, int wallet, int amount) {
        GroupID = groupID;
        UserID = userID;
        Wallet = wallet;
        Amount = amount;
        GroupName = groupName;
    }

    public int getRequestAmount(Context context){

        DBHelper db=new DBHelper(context);

        List<Integer> users= db.getUsersJoindGroup(GroupID);

        int amount=Amount/users.size();
        return amount;
    }

    public void sendRequests(Context context){
        DBHelper db=new DBHelper(context);

        List<Integer> users= db.getUsersJoindGroup(GroupID);

        for( int i : users){
            Request request=new Request(0,GroupID,i,false);
            db.sendRequest(request);
        }
    }
}
