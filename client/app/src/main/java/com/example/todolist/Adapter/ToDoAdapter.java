package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.Data.ToDoList;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.RequestHandler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private ToDoList todoList;
    private final MainActivity activity;
    public ToDoAdapter(MainActivity activity,ToDoList toDoList) {
        this.todoList = toDoList;

        this.activity = activity;
    }

    public Context getContext() {
        return this.activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.checkbox.setText(this.todoList.getToDoList().get(position).getTask());
        holder.checkbox.setChecked(this.todoList.getToDoList().get(position).getStatus());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int taskPosition = holder.getBindingAdapterPosition();
                todoList.getToDoList().get(taskPosition).setStatus(b, null);
            }
        });
        holder.checkbox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                editItem(holder.getAbsoluteAdapterPosition());
                return true;
            }
        });
    }
    public void editItem(int position){
        ToDoModel editingToDoModel = todoList.getToDoList().get(position);
        AddNewTask dialog = new AddNewTask(editingToDoModel, new AddNewTask.Callback() {
            @Override
            public void saveClickCallback(ToDoModel toDoModel) {
                editingToDoModel.setTask(toDoModel.getTask(), new RequestHandler.RequestCallback() {
                        @Override
                        public void onResponseSucceed(@NonNull Response response) {
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemChanged(position);
                                }
                            });
                        }
                        @Override
                        public void onResponseFailure(@NonNull Response response) {}
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {}
                    });
            }
            @Override
            public void dismissCallback() {
                notifyItemChanged(position);
            }
        });
        dialog.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }
    public void addNewItem(){
        AddNewTask dialog = new AddNewTask(new AddNewTask.Callback() {
            @Override
            public void saveClickCallback(ToDoModel toDoModel) {
                todoList.addTask(toDoModel, new RequestHandler.RequestCallback() {
                    @Override
                    public void onResponseSucceed(@NonNull Response response) {
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                int newTaskPosition = todoList.getToDoList().size();
                                notifyItemInserted(newTaskPosition);
                            }
                        });
                    }
                    @Override
                    public void onResponseFailure(@NonNull Response response) {
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),
                                        "Add new task failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),
                                        "Add new task failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            @Override
            public void dismissCallback() {}
        });
        dialog.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }
    public void removeItem(int position){
        todoList.deleteTask(position, new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed(@NonNull Response response) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemRemoved(position);
                    }
                });

            }

            @Override
            public void onResponseFailure(@NonNull Response response) {}
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {}
        });
    }

    @Override
    public int getItemCount() {
        return todoList.getToDoList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.todoCheckBox);
        }
    }
}
