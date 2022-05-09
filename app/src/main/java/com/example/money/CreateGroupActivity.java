package com.example.money;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.money.adapter.UserAdapter;
import com.example.money.database.DBHelper;
import com.example.money.database.Group;
import com.example.money.database.GroupUser;
import com.example.money.database.User;
import com.example.money.session.SessionManager;
import com.example.money.util.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateGroupActivity extends AppCompatActivity {

    DBHelper db;
    List<User> users;
    UserAdapter mAdapter;
    SessionManager sessionManager;
    int userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_group);

        sessionManager= SessionManager.Companion.getInstance(getApplicationContext());
        sessionManager.ifUserLoggedOut(this);

        userID=sessionManager.getUserID();

        db=new DBHelper(getApplicationContext());
        users=db.getUsers(userID);

        GridView gridView=findViewById(R.id.gridView);

        final List<Integer> clickedUsers=new ArrayList<Integer>();

        mAdapter=new UserAdapter(this,users);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Utility.Companion.filter(clickedUsers,(int)id)) {
                    clickedUsers.add((int)id);
                    setBackground(view, R.drawable.box_colored);
                } else {
                    setBackground(view, R.drawable.bg_transparent);
                    clickedUsers.removeAll(new ArrayList<Integer>(Arrays.asList((int)id)));
                }
            }
        });

        Button btnSaveGroup=findViewById(R.id.btnSaveGroup);
        final EditText edtGroupName,edtWallet;
        edtGroupName=findViewById(R.id.edtGroupName);
        edtWallet=findViewById(R.id.edtWallet);

        btnSaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Text.Companion.isEmpty(edtGroupName,edtWallet))
                    if(!clickedUsers.isEmpty()){
                        int groupID=db.addGroup(new Group(0,edtGroupName.getText().toString(),userID,0,Integer.parseInt(edtWallet.getText().toString())));
                        for(int i : clickedUsers){
                            GroupUser groupUser=new GroupUser(0,groupID,i);
                            db.addUserGroup(groupUser);
                        }

                        Utility.Companion.viewMessage(CreateGroupActivity.this,"created successfully");
                        finish();
                    }
            }
        });
    }


    private void setBackground(View v,int resource) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, resource));
        } else {
            v.setBackground(ContextCompat.getDrawable(this, resource));
        }
    }
}
