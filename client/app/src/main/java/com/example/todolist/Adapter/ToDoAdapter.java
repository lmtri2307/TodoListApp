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

import com.example.todolist.API.ToDoAPI;
import com.example.todolist.AddNewTask;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDo;
import com.example.todolist.Model.User;
import com.example.todolist.R;
import com.example.todolist.Utils.Converter;
import com.example.todolist.Utils.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDo> toDoList ;
    private final MainActivity activity;
    public ToDoAdapter(MainActivity activity) {
        toDoList = new ArrayList<>();
        ToDoAPI.getData(new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed(@NonNull Response response) {
                try {
                    toDoList = Converter.fromJsonString(Objects.requireNonNull(response.body()).string(),
                            new TypeReference<List<ToDo>>() {});
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onResponseFailure(@NonNull Response response) {}

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {}
        });
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

        holder.checkbox.setText(toDoList.get(position).getTask());
        holder.checkbox.setChecked(toDoList.get(position).getStatus());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int taskPosition = holder.getBindingAdapterPosition();
                ToDo checkedToDo = toDoList.get(taskPosition);
                ToDoAPI.updateStatus(checkedToDo, b, new RequestHandler.RequestCallback() {
                    @Override
                    public void onResponseSucceed(@NonNull Response response) {
                        checkedToDo.setStatus(b);
                    }

                    @Override
                    public void onResponseFailure(@NonNull Response response) {}

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {}
                });
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
        ToDo editingToDo = toDoList.get(position);
        AddNewTask dialog = new AddNewTask(editingToDo, new AddNewTask.Callback() {
            @Override
            public void saveClickCallback(ToDo toDo) {
                ToDoAPI.updateTask(toDo, toDo.getTask(), new RequestHandler.RequestCallback() {
                        @Override
                        public void onResponseSucceed(@NonNull Response response) {
                            try {
                                ToDo updatedToDo = Converter.fromJsonString(
                                        Objects.requireNonNull(response.body()).string(),
                                        ToDo.class);
                                toDoList.remove(position);
                                toDoList.add(position, updatedToDo);
                                Handler mainHandler = new Handler(Looper.getMainLooper());
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        notifyItemChanged(position);
                                    }
                                });
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
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
            public void saveClickCallback(ToDo toDoModel) {
                ToDoAPI.addTask(toDoModel, new RequestHandler.RequestCallback() {
                    @Override
                    public void onResponseSucceed(@NonNull Response response) {
                        try {
                            ToDo newToDo = Converter.fromJsonString(
                                    Objects.requireNonNull(response.body()).string(),
                                    ToDo.class);
                            toDoList.add(newToDo);
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int newTaskPosition = toDoList.size();
                                    notifyItemInserted(newTaskPosition);
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
        ToDoAPI.deleteTask(toDoList.get(position), new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed(@NonNull Response response) {
                toDoList.remove(position);

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
        return toDoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.todoCheckBox);
        }
    }
}
