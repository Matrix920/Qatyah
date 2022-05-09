package com.example.money.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.money.R;
import com.example.money.database.DBHelper;
import com.example.money.database.User;

public class PersonalFragment extends Fragment {

    int userID;
    DBHelper db;
    TextView budget;

    public PersonalFragment(int userID){
        this.userID=userID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView= inflater.inflate(R.layout.fragment_personal, container, false);

        TextView username=mView.findViewById(R.id.txtUsername);
        budget=mView.findViewById(R.id.txtBudget);

        DBHelper db=new DBHelper(getContext());
        User user=db.getUser(userID);

        username.setText(user.Username);
        budget.setText(String.valueOf(user.Budget));

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
//        User user=db.getUser(userID);
//        budget.setText(String.valueOf(user.Budget));
    }
}
