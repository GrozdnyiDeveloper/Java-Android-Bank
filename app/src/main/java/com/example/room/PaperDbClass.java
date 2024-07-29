package com.example.room;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import io.paperdb.Paper;

public class PaperDbClass {

    public User getUser(){
        return Paper.book("currentUser").read("currentUser", null);
    }

    public void saveUser(User user){
        Paper.book("currentUser").write("currentUser", user);
    }
}
