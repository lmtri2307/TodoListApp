package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Data.ToDoList;
import com.example.todolist.Utils.RequestHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public class HomeFragment extends Fragment {
    private ToDoAdapter tasksAdapter;
    public ToDoList toDoList;
    RecyclerView tasksRecyclerView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
//

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // get Data
        toDoList = new ToDoList();
        toDoList.getData(new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed(@NonNull Response response) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tasksAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onResponseFailure(@NonNull Response response) {}

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {}
        });


        // View
        tasksRecyclerView = view.findViewById(R.id.tasksRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        tasksRecyclerView.setLayoutManager(layoutManager);
        tasksAdapter = new ToDoAdapter((MainActivity) this.getActivity(), toDoList);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksAdapter.addNewItem();
            }
        });
        return view;
    }
}