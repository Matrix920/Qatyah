package com.example.money.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.money.R;
import com.example.money.database.Group;
import com.example.money.database.User;
import com.example.money.util.Utility;

import java.util.List;


public class GroupAdapter extends   BaseAdapter {
    List<Group>groups;
    Context mContext;

    public GroupAdapter(Context mContext, List<Group> groups){
        this.mContext=mContext;
        this.groups=groups;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Group getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).GroupID;
    }

    @Override
    public View getView(int position, View mView, ViewGroup parent) {
        if(mView==null)
            mView= LayoutInflater.from(mContext).inflate(R.layout.item_group, parent,false);

        final Group group=getItem(position);

        TextView txtName=(TextView) mView.findViewById(R.id.txtGroupName);
        TextView txtAmount=mView.findViewById(R.id.txtAmount);
        TextView txtWallet=mView.findViewById(R.id.txtWallet);
        Button btn=mView.findViewById(R.id.btnGet);


        txtName.setText(group.GroupName);
        txtAmount.setText(String.valueOf(group.Amount));
        txtWallet.setText(String.valueOf(group.Wallet));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.sendRequests(mContext);
                Utility.Companion.viewMessage(mContext,"Money request was sent to all group members");
            }
        });

        return mView;
    }

}
