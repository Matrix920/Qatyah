package com.example.money.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.money.R;
import com.example.money.database.DBHelper;
import com.example.money.database.Group;
import com.example.money.database.Request;
import com.example.money.database.User;

import java.util.List;


public class RequestAdapter extends   BaseAdapter {
    List<Request>requests;
    Context mContext;

    public RequestAdapter(Context mContext, List<Request> requests){
        this.mContext=mContext;
        this.requests=requests;
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Request getItem(int position) {
        return requests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).RequestID;
    }

    @Override
    public View getView(int position, View mView, ViewGroup parent) {
        if(mView==null)
            mView= LayoutInflater.from(mContext).inflate(R.layout.item_notification, parent,false);

        final Request request=getItem(position);

        TextView txtName=(TextView) mView.findViewById(R.id.txtGroupName);
        TextView txtAmount=mView.findViewById(R.id.txtAmount);
        Button btn=mView.findViewById(R.id.btnAccept);

        final DBHelper db=new DBHelper(mContext);
        Group group=db.getGroup(request.GroupID);

        txtName.setText(group.GroupName);
        txtAmount.setText(String.valueOf(group.getRequestAmount(mContext)));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.acceptRequest(mContext);

                requests=db.getUserRequests(request.UserID);
                notifyDataSetChanged();
            }
        });

        return mView;
    }

}
