package com.example.money;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.money.database.DBHelper;
import com.example.money.session.SessionManager;

public class LoginActivity extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Button btnRegister,btnLogin;
        final EditText edtUsername,edtPassword;
        btnRegister=findViewById(R.id.btnRegister);
        btnLogin=findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }


        });

        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edtUsername.getText().toString();
                String password=edtPassword.getText().toString();

                DBHelper db= new DBHelper(getApplicationContext());
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    int id = db.login(username, password);
                    if (id != 0) {
                        sessionManager.login(LoginActivity.this, id);
                    } else {
                        Toast.makeText(LoginActivity.this, "authentication failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        sessionManager = SessionManager.Companion.getInstance(getApplicationContext());
        sessionManager.checkLogin(this);
    }
}
