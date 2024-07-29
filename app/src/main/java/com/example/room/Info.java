package com.example.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import io.paperdb.Paper;

public class Info extends AppCompatActivity {

    EditText userLoginEdit, userPasswordEdit;
    TextView userId, userLogin, userPassword, userName, userBalance;
    BottomNavigationView bottomNavigationView;
    Button buttonBack, buttonChange;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        userId = findViewById(R.id.user_id);
        userLogin = findViewById(R.id.user_login);
        userPassword = findViewById(R.id.user_password);
        userName = findViewById(R.id.user_name);
        buttonBack = findViewById(R.id.buttonBck);
        userBalance = findViewById(R.id.user_balance);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        userLoginEdit = findViewById(R.id.login_user);
        userPasswordEdit = findViewById(R.id.password_user);
        buttonChange = findViewById(R.id.buttonChange);

        id = getIntent().getIntExtra("currentUserId", 0);

        Paper.init(this);
        PaperDbClass paperDbClass = new PaperDbClass();
        fetchUsers();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(
                        "",
                        "",
                        "",
                        0
                );
                paperDbClass.saveUser(user);
                Intent intent = new Intent(getApplicationContext(), Authorization.class);
                startActivity(intent);
            }
        });

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
                List<User> users = db.userDao().getUsers();
                for (int i = 0; i < users.size(); i++){
                    if (id == users.get(i).getId())
                    {
                        User user = new User(
                                id,
                                userLoginEdit.getText().toString(),
                                userPasswordEdit.getText().toString(),
                                users.get(i).getUserName(),
                                users.get(i).getUserBalance()
                        );
                        db.userDao().updateUser(user);
                        fetchUsers();
                    }
                }
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.first_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.first_item:

                        return true;

                    case R.id.second_item:
                        Intent intent = new Intent(getApplicationContext(), GainsWindow.class);
                        intent.putExtra("currentUserId", id);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void fetchUsers()
    {
        AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
        List<User> users = db.userDao().getUsers();
        for (int i = 0; i < users.size(); i++){
            if (id == users.get(i).getId())
            {
                userId.setText("Id: " + String.valueOf(users.get(i).getId()));
                userLogin.setText("Login: " + String.valueOf(users.get(i).getLogin()));
                userPassword.setText("Password: " + String.valueOf(users.get(i).getPassword()));
                userName.setText("Username: " + String.valueOf(users.get(i).getUserName()));
                userBalance.setText("Balance: " + String.valueOf(users.get(i).getUserBalance()));
            }
        }
    }
}