package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Data.ToDoList;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.RequestHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity mainAct = this;
        RequestHandler.setTokenExpiredCallback(new RequestHandler.TokenExpiredCallback() {
            @Override
            public void onTokenExpired() {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Navigation.findNavController(mainAct,R.id.fragmentContainerView)
                                .navigate(R.id.loginFragment);
                    }
                });

            }
        });

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
}