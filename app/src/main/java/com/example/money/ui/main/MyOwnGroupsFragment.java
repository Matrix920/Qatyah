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
import com.example.money.adapter.GroupAdapter;
import com.example.money.database.DBHelper;
import com.example.money.database.Group;

import java.util.List;

public class MyOwnGroupsFragment extends Fragment {

    GroupAdapter mAdapter;
    DBHelper db;
    int userID;

    public MyOwnGroupsFragment(int userID){
        this.userID=userID;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_my_own_groups, container, false);

        db= new DBHelper(getContext());
        List<Group> groups=db.getUserOwnGroups(userID);

        ListView list =mView.findViewById(R.id.listGroups);
        mAdapter= new GroupAdapter(requireContext(),groups);
        list.setAdapter(mAdapter);

        return mView;
    }

}
