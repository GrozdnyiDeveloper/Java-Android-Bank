package com.example.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import io.paperdb.Paper;

public class Authorization extends AppCompatActivity {

    TextView errorMsg;
    EditText loginUser, passwordUser;
    Button toReg, authorize;
    boolean bruh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        errorMsg = findViewById(R.id.errorMsg);
        loginUser = findViewById(R.id.login);
        passwordUser = findViewById(R.id.password);
        toReg = findViewById(R.id.buttonToReg);
        authorize = findViewById(R.id.buttonAuth);

        Paper.init(this);
        PaperDbClass paperDbClass = new PaperDbClass();
        if (paperDbClass.getUser() != null) {
            if (paperDbClass.getUser().getLogin() != "")
            {
                Intent intent = new Intent(getApplicationContext(), Info.class);
                intent.putExtra("currentUserId", paperDbClass.getUser().getId());
                startActivity(intent);
            }
        }

        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });

        authorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
                List<User> users = db.userDao().getUsers();
                bruh = false;
                for (int i = 0; i < users.size(); i++){
                    if (loginUser.getText().toString().equals(users.get(i).getLogin()) && passwordUser.getText().toString().equals(users.get(i).getPassword()))
                    {
                        errorMsg.setText("Авторизация успешна");
                        Intent intent = new Intent(getApplicationContext(), Info.class);
                        paperDbClass.saveUser(users.get(i));
                        intent.putExtra("currentUserId", users.get(i).getId());
                        startActivity(intent);
                        bruh = true;
                    }
                }
                if (!bruh) errorMsg.setText("Пользователь не найден");
            }
        });
    }
}