package com.example.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.money.database.DBHelper;
import com.example.money.database.User;
import com.example.money.session.SessionManager;
import com.example.money.util.Utility;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnRegister=findViewById(R.id.btnRegister);

        final EditText edtUsername,edtPassword;
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! Text.Companion.isEmpty(edtUsername,edtPassword)) {
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();

                    DBHelper db =new DBHelper(getApplicationContext());

                    User user =new  User(0, username, password, 1000000);

                    db.register(user);

                    Utility.Companion.viewMessage(RegisterActivity.this, "Registered successfully");
                    Intent i =new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "املىء جميع الحقول من فضلك", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SessionManager.Companion.getInstance(getApplicationContext()).checkLogin(this);
    }
}
