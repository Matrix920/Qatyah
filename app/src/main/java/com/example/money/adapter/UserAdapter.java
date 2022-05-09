package com.example.money.adapter;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.money.R;
import com.example.money.database.User;

import java.util.List;


public class UserAdapter extends   BaseAdapter {
    List<User>users;
    Context mContext;

    public UserAdapter(Context mContext, List<User> users){
        this.mContext=mContext;
        this.users=users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).UserID;
    }

    @Override
    public View getView(int position, View mView, ViewGroup parent) {
        if(mView==null)
            mView= LayoutInflater.from(mContext).inflate(R.layout.item_user, parent,false);

        TextView txtName=(TextView) mView.findViewById(R.id.txtName);

        txtName.setText(getItem(position).Username);

        return mView;
    }

}
