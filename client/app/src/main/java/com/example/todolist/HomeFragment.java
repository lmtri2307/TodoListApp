package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.Adapter.ToDoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class HomeFragment extends Fragment {
    private ToDoAdapter todoAdapter;
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
        todoAdapter = new ToDoAdapter((MainActivity) this.getActivity());

        // View
        tasksRecyclerView = view.findViewById(R.id.tasksRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        tasksRecyclerView.setLayoutManager(layoutManager);
        tasksRecyclerView.setAdapter(todoAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(todoAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoAdapter.addNewItem();
            }
        });
        return view;
    }
}