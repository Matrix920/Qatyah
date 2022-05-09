package com.example.money.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.money.R;
import com.example.money.adapter.RequestAdapter;
import com.example.money.database.DBHelper;
import com.example.money.database.Request;

import java.util.List;

public class NotificationsFragment extends Fragment {
    int userID;
    DBHelper db;
    RequestAdapter mAdapter;

    public NotificationsFragment(int userID){
        this.userID=userID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_notifications, container, false);

        db= new DBHelper(getContext());
        List<Request> requests=db.getUserRequests(userID);

        ListView listView =mView.findViewById(R.id.listRequests);
        mAdapter=new RequestAdapter(requireContext(),requests);
        listView.setAdapter(mAdapter);

        return mView;
    }


}
