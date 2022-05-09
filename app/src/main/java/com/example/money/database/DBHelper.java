package com.example.money.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.money.database.Group;
import com.example.money.database.GroupUser;
import com.example.money.database.Request;
import com.example.money.database.User;
import com.example.money.util.Utility;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context mContext) {

        super(mContext, DB_NAME, null, 1);
    }

    public static final String ID = "ID";
    public static final String DB_NAME = "MoneyDB";

    public static final String USERS = User.USERS;
    public static final String GROUPS = Group.GROUPS;
    public static final String REQUESTS = Request.REQUESTS;
    public static final String GROUP_USERS = GroupUser.GROUP_USERS;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Users(UserID integer primary key autoincrement,Username text,Password text,Budget integer)");
        db.execSQL("create table Groups(GroupID integer primary key autoincrement , GroupName text,Amount integer,Wallet integer,UserID integer)");
        db.execSQL("create table Requests(RequestID integer primary key autoincrement , UserID integer,GroupID integer,Response integer)");
        db.execSQL("create table GroupUsers(ID integer primary key autoincrement,UserID integer,GroupID integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Users");
        db.execSQL("drop table if exists Groups");
        db.execSQL("drop table if exists GroupsUsers");
        db.execSQL("drop table if exists Requests");

        onCreate(db);
    }


    public void register(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(User.BUDGET, user.Budget);
        c.put(User.PASSWORD, user.Password);
        c.put(User.USERNAME, user.Username);

        db.insert(USERS, null, c);
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(User.BUDGET, user.Budget);
        c.put(User.PASSWORD, user.Password);
        c.put(User.USERNAME, user.Username);

        db.update(USERS, c, "UserID=?", new String[]{String.valueOf(user.UserID)});
    }

    public List<User> getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from Users", null);
        List<User> users = new ArrayList<User>();

        if (res.moveToFirst()) {
            do {
                String username = res.getString(res.getColumnIndex(User.USERNAME));
                int userID = res.getInt(res.getColumnIndex(User.USER_ID));
                int budget = res.getInt(res.getColumnIndex(User.BUDGET));
                String password = res.getString(res.getColumnIndex(User.PASSWORD));

                User user = new User(userID, username, password, budget);
                users.add(user);

                res.moveToNext();
            } while (res.isAfterLast() == false);
        }

        return users;
    }

    public User getUser(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User(0, "", "", 0);

        Cursor res = db.rawQuery("select * from Users where UserID=" + userID, null);
        if (res.moveToFirst()) {
            String username = res.getString(res.getColumnIndex(User.USERNAME));
            int budget = res.getInt(res.getColumnIndex(User.BUDGET));
            String password = res.getString(res.getColumnIndex(User.PASSWORD));

            user = new User(userID, username, password, budget);
        }

        return user;
    }

    public List<User> getUsers(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from Users where UserID!=" + userID, null);
        List<User> users = new ArrayList<User>();

        if (res.moveToFirst()) {
            do {
                String username = res.getString(res.getColumnIndex(User.USERNAME));
                int budget = res.getInt(res.getColumnIndex(User.BUDGET));
                String password = res.getString(res.getColumnIndex(User.PASSWORD));
                int userID_ = res.getInt(res.getColumnIndex(User.USER_ID));
                User user = new User(userID_, username, password, budget);
                users.add(user);

                res.moveToNext();
            } while (res.isAfterLast() == false);
        }

        return users;
    }

    public int addGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(Group.AMOUNT, group.Amount);
        c.put(Group.GROUP_NAME, group.GroupName);
        c.put(Group.WALLET, group.Wallet);
        c.put(User.USER_ID, group.UserID);

        return (int) db.insert(GROUPS, null, c);
    }

    public void updateGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(Group.AMOUNT, group.Amount);
        c.put(Group.GROUP_NAME, group.GroupName);
        c.put(Group.WALLET, group.Wallet);

        db.update(GROUPS, c, "GroupID=?", new String[]{String.valueOf(group.GroupID)});
    }

    public Group getGroup(int groupID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Group group = new Group(0, "", 0, 0, 0);
        Cursor res = db.rawQuery("select * from Groups where GroupID=" + groupID, null);
        if (res.moveToFirst()) {
            String name = res.getString(res.getColumnIndex(Group.GROUP_NAME));
            int amount = res.getInt(res.getColumnIndex(Group.AMOUNT));
            int wallet = res.getInt(res.getColumnIndex(Group.WALLET));
            int userID = res.getInt(res.getColumnIndex(User.USER_ID));

            group = new Group(groupID, name, userID, wallet, amount);
        }

        return group;
    }

    public List<Group> getUserOwnGroups(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Group> groups = new ArrayList<Group>();
        Cursor res = db.rawQuery("select * from Groups where UserID=" + userID, null);
        if (res.moveToFirst()) {
            do {
                String name = res.getString(res.getColumnIndex(Group.GROUP_NAME));
                int amount = res.getInt(res.getColumnIndex(Group.AMOUNT));
                int wallet = res.getInt(res.getColumnIndex(Group.WALLET));
                int groupID = res.getInt(res.getColumnIndex(Group.GROURP_ID));

                Group group = new Group(groupID, name, userID, wallet, amount);
                groups.add(group);

                res.moveToNext();
            } while (res.isAfterLast() == false);
        }
        return groups;
    }

    public List<Integer> getUsersJoindGroup(int groupID) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Integer> list = new ArrayList<Integer>();
        Cursor res = db.rawQuery("select * from GroupUsers where GroupID=" + groupID, null);
        if (res.moveToFirst()) {
            do {
                int userID = res.getInt(res.getColumnIndex(User.USER_ID));
                list.add(userID);

                res.moveToNext();
            } while (res.isAfterLast() == false);
        }

        return list;
    }

    public void sendRequest(Request request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(Request.RESPONSE, Utility.Companion.getInt(request.Response));
        c.put(Group.GROURP_ID, request.GroupID);
        c.put(User.USER_ID, request.UserID);

        db.insert(REQUESTS, "", c);
    }

    public void updateRequest(Request request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put(Request.RESPONSE, Utility.Companion.getInt(request.Response));
        c.put(Group.GROURP_ID, request.GroupID);
        c.put(User.USER_ID, request.UserID);

        db.update(REQUESTS, c, "RequestID=?", new String[]{String.valueOf(request.RequestID)});
    }

    public List<Request> getUserRequests(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Request> requests = new ArrayList<Request>();
        Cursor res = db.rawQuery("select * from Requests where Response=0 and UserID=" + userID, null);
        if (res.moveToFirst()) {
            do {
                int requestID = res.getInt(res.getColumnIndex(Request.REQUEST_ID));
                int groupID = res.getInt(res.getColumnIndex(Group.GROURP_ID));

                Request request = new Request(requestID, groupID, userID, false);
                requests.add(request);
                res.moveToNext();
            } while (res.isAfterLast() == false);
        }

        return requests;
    }

    public void addUserGroup(GroupUser u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(User.USER_ID, u.UserID);
        c.put(Group.GROURP_ID, u.GroupID);

        db.insert(GROUP_USERS, null, c);
    }

    public int login(String username, String password) {
        int userID = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Users where Username='" + username + "' and Password='" + password + "'", null);
        if (res.moveToFirst()) ;
        userID = res.getInt(res.getColumnIndex(User.USER_ID));

        return userID;
    }

}
