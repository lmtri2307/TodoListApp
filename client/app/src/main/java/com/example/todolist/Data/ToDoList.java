package com.example.todolist.Data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.Converter;
import com.example.todolist.Utils.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class ToDoList {
    private List<ToDoModel> toDoList = new ArrayList<>();

    public void createFakeData() {
        toDoList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ToDoModel a = new ToDoModel(""+i,false, "Task number " + i);
            toDoList.add(a);
        }
    }

    public List<ToDoModel> getToDoList() {
        return toDoList;
    }

    public void addTask(ToDoModel toDoModel){
        addTask(toDoModel, null);
    }
    public void addTask(ToDoModel toDoModel, @Nullable RequestHandler.RequestCallback addTaskCallback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", toDoModel.getStatus());
            jsonObject.put("task", toDoModel.getTask());
            RequestHandler.post(false, "/todo", jsonObject, new RequestHandler.RequestCallback() {
                @Override
                public void onResponseSucceed(@NonNull Response response) {
                    try {
                        ToDoModel newToDoModel = Converter.fromJsonString(
                                Objects.requireNonNull(response.body()).string(),
                                ToDoModel.class);
                        toDoList.add(newToDoModel);
                        Objects.requireNonNull(addTaskCallback).onResponseSucceed(response);
                    } catch (IOException e) {
                        this.onResponseFailure(response);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponseFailure(@NonNull Response response) {
                    Objects.requireNonNull(addTaskCallback).onResponseFailure(response);
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Objects.requireNonNull(addTaskCallback).onFailure(call, e);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void deleteTask(int position,@Nullable RequestHandler.RequestCallback callback) {
        ToDoModel toBeDeleted = toDoList.get(position);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("todoId", toBeDeleted.getId());

            RequestHandler.delete(false, "/todo", jsonObject,
                    new RequestHandler.RequestCallback() {
                        @Override
                        public void onResponseSucceed(@NonNull Response response) {
                            toDoList.remove(position);
                            if (callback != null) {
                                callback.onResponseSucceed(response);
                            }
                        }
                        @Override
                        public void onResponseFailure(@NonNull Response response) {
                            if (callback != null) {
                                callback.onResponseFailure(response);
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            if (callback != null) {
                                callback.onFailure(call, e);
                            }
                        }
                    });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTask(ToDoModel toDoModel) {
        toDoList.remove(toDoModel);
    }

    public void getData(){
        getData(null);
    }
    public void getData(@Nullable RequestHandler.RequestCallback callback) {
        RequestHandler.get(false,"/todo", new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed( @NonNull Response response) {
                try {
                    toDoList = Converter.fromJsonString(Objects.requireNonNull(response.body()).string(),
                            new TypeReference<List<ToDoModel>>() {}
                            );
                    if (callback != null) {
                        callback.onResponseSucceed(response);
                    }
                    Objects.requireNonNull(callback).onResponseSucceed(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onResponseFailure(@NonNull Response response) {
                if (callback != null) {
                    callback.onResponseFailure(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }
        });
    }
}
