package com.example.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class GainsWindow extends AppCompatActivity {

    PieChart pieChartGains, pieChartFalls;
    EditText textName, textPrice;
    TextView textBalance;
    Button buttonAddGains, buttonAddFalls, buttonDel, buttonBack;
    BottomNavigationView bottomNavigationView;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gains_window);

        textBalance = findViewById(R.id.textBalance);
        buttonAddGains = findViewById(R.id.buttonAddGains);
        buttonAddFalls = findViewById(R.id.buttonAddFalls);
        buttonDel = findViewById(R.id.buttonDel);
        buttonBack = findViewById(R.id.buttonBck);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        textName = findViewById(R.id.name_gains);
        textPrice = findViewById(R.id.price_gains);
        pieChartGains = findViewById(R.id.PieChartGains);
        pieChartFalls = findViewById(R.id.PieChartFalls);

        id = getIntent().getIntExtra("currentUserId", 0);
        Paper.init(this);
        PaperDbClass paperDbClass = new PaperDbClass();
        fetchGains();

        bottomNavigationView.setSelectedItemId(R.id.second_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.first_item:
                        Intent intent = new Intent(getApplicationContext(), Info.class);
                        intent.putExtra("currentUserId", id);
                        startActivity(intent);
                        return true;

                    case R.id.second_item:

                        return true;
                }
                return false;
            }
        });

        buttonAddGains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gains gains = new Gains(
                        true,
                        textName.getText().toString(),
                        Integer.parseInt(textPrice.getText().toString()),
                        id
                );
                AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
                db.gainsDao().insertGains(gains);
                List<User> users = db.userDao().getUsers();
                for (int i = 0; i < users.size(); i++){
                    if (id == users.get(i).getId())
                    {
                        User user = new User(
                                id,
                                users.get(i).getLogin(),
                                users.get(i).getPassword(),
                                users.get(i).getUserName(),
                                users.get(i).getUserBalance() + Integer.parseInt(textPrice.getText().toString())
                        );
                        db.userDao().updateUser(user);
                    }
                }
                fetchGains();
            }
        });

        buttonAddFalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gains gains = new Gains(
                        false,
                        textName.getText().toString(),
                        Integer.parseInt(textPrice.getText().toString()),
                        id
                );
                AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
                db.gainsDao().insertGains(gains);
                List<User> users = db.userDao().getUsers();
                for (int i = 0; i < users.size(); i++){
                    if (id == users.get(i).getId())
                    {
                        User user = new User(
                                id,
                                users.get(i).getLogin(),
                                users.get(i).getPassword(),
                                users.get(i).getUserName(),
                                users.get(i).getUserBalance() - Integer.parseInt(textPrice.getText().toString())
                        );
                        db.userDao().updateUser(user);
                    }
                }
                fetchGains();
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
                db.gainsDao().deleteAllGains();
                fetchGains();
            }
        });

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
    }

    public void fetchGains()
    {
        AppDataBase db = AppDataBase.getDbInstance(getApplicationContext());
        List<User> users = db.userDao().getUsers();
        for (int i = 0; i < users.size(); i++){
            if (id == users.get(i).getId())
            {
                textBalance.setText("Balance: " + String.valueOf(users.get(i).getUserBalance()));
            }
        }

        List<Gains> gains = db.gainsDao().getGainsType(id, true);
        ArrayList<PieEntry> gainsPie = new ArrayList<PieEntry>();
        for (int i = 0; i < gains.size(); i++){
            gainsPie.add(new PieEntry(gains.get(i).getGainsPrice(), gains.get(i).getGainsInfo()));
        }

        PieDataSet pieDataSetGains = new PieDataSet(gainsPie, "");
        pieDataSetGains.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSetGains.setValueTextColor(Color.BLACK);
        pieDataSetGains.setValueTextSize(12f);
        PieData pieDataGains = new PieData(pieDataSetGains);
        pieChartGains.setData(pieDataGains);
        pieChartGains.getDescription().setEnabled(false);
        pieChartGains.setCenterText("История пополнений");
        pieChartGains.animate();

        List<Gains> falls = db.gainsDao().getGainsType(id, false);
        ArrayList<PieEntry> fallsPie = new ArrayList<PieEntry>();
        for (int i = 0; i < falls.size(); i++){
            fallsPie.add(new PieEntry(falls.get(i).getGainsPrice(), falls.get(i).getGainsInfo()));
        }

        PieDataSet pieDataSetFalls = new PieDataSet(fallsPie, "");
        pieDataSetFalls.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSetFalls.setValueTextColor(Color.BLACK);
        pieDataSetFalls.setValueTextSize(12f);
        PieData pieDataFalls = new PieData(pieDataSetFalls);
        pieChartFalls.setData(pieDataFalls);
        pieChartFalls.getDescription().setEnabled(false);
        pieChartFalls.setCenterText("История списаний");
        pieChartFalls.animate();
    }
}