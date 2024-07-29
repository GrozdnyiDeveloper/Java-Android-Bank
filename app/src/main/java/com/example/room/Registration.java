package com.example.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class Registration extends AppCompatActivity {

    EditText userLogin, userPassword, userName;
    Button registerButton, backButton, buttonDel;
    TextView allUsers, errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLogin = findViewById(R.id.login_user);
        userPassword = findViewById(R.id.password_user);
        userName = findViewById(R.id.name_user);
        registerButton = findViewById(R.id.buttonReg);
        allUsers = findViewById(R.id.users);
        backButton = findViewById(R.id.buttonExt);
        buttonDel = findViewById(R.id.buttonDel);
        errorMsg = findViewById(R.id.errorMsg);


        fetchUsers();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userLogin.getText().toString().equals("") && !userPassword.getText().toString().equals("") && !userName.getText().toString().equals(""))
                {
                    AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
                    List<User> users = db.userDao().getUsers();
                    boolean exist = false;
                    for (int i = 0; i < users.size(); i++)
                        if (users.get(i).getLogin().equals(userLogin.getText().toString()) || users.get(i).getPassword().equals(userPassword.getText().toString()) || users.get(i).getUserName().equals(userName.getText().toString()))
                            exist = true;
                    if (!exist)
                    {
                        User user = new User(
                                userLogin.getText().toString(),
                                userPassword.getText().toString(),
                                userName.getText().toString(),
                                0
                        );
                        db.userDao().insertUser(user);
                        fetchUsers();
                    }
                    else
                    {
                        errorMsg.setText("Уже существует пользователь с введёнными данными!");
                    }
                }
                else
                {
                    errorMsg.setText("Все поля должны быть заполнены!");
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Authorization.class);
                startActivity(intent);
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
                db.userDao().deleteAll();
                fetchUsers();
            }
        });
    }

    public void fetchUsers()
    {
        allUsers.setText("");
        AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
        List<User> users = db.userDao().getUsers();
        for (int i = 0; i < users.size(); i++){
            allUsers.append("Id:" + users.get(i).getId() + "; Login: " + users.get(i).getLogin() + "; Password: " + users.get(i).getPassword() + "; Name: " + users.get(i).getUserName()+ "; Balance: " + users.get(i).getUserBalance() + "\n");
        }
    }
}