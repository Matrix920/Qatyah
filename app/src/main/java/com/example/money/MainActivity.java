package com.example.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.money.database.DBHelper;
import com.example.money.database.User;
import com.example.money.session.SessionManager;
import com.example.money.ui.main.MyOwnGroupsFragment;
import com.example.money.ui.main.NotificationsFragment;
import com.example.money.ui.main.PersonalFragment;
import com.example.money.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;
    int userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        insertUsers();

        sessionManager=SessionManager.Companion.getInstance(getApplicationContext());
        sessionManager.ifUserLoggedOut(this);

        userID=sessionManager.getUserID();

        List<Fragment> fragments=getFragments();

        List<String> titles= new ArrayList<>(Arrays.asList("My Groups","Personal","Notifications")) ;

        SectionsPagerAdapter sectionsPagerAdapter =new SectionsPagerAdapter(this, getSupportFragmentManager(),fragments,titles);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,CreateGroupActivity.class);
                startActivity(i);
            }
        });
    }

    private List<Fragment> getFragments(){
        List<Fragment> fragments=new ArrayList<>(Arrays.asList(new MyOwnGroupsFragment(userID),new PersonalFragment(userID),new NotificationsFragment(userID)));
        return fragments;
    }

    private void insertUsers(){
        DBHelper db=new DBHelper(getApplicationContext());

        if(db.getUsers().size()<10)
            for(int i=1;i<11;i++){
            User user=new User(0,"user"+i,String.valueOf(i),1000000);
            db.register(user);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemLogout:{
                sessionManager.logout(MainActivity.this);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
