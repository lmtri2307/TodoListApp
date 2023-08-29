package com.example.todolist.API;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Model.ToDo;
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

public class ToDoAPI {
    public static void getData(@Nullable RequestHandler.RequestCallback callback) {
        RequestHandler.get(false,"/todo/all", new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed( @NonNull Response response) {
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
    }

    public static void addTask(ToDo toDo, @Nullable RequestHandler.RequestCallback addTaskCallback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", toDo.getStatus());
            jsonObject.put("task", toDo.getTask());

            RequestHandler.post(false, "/todo/new", jsonObject, new RequestHandler.RequestCallback() {
                @Override
                public void onResponseSucceed(@NonNull Response response) {
                    if(addTaskCallback != null){
                        addTaskCallback.onResponseSucceed(response);
                    }
                }

                @Override
                public void onResponseFailure(@NonNull Response response) {
                    if(addTaskCallback != null){
                        addTaskCallback.onResponseFailure(response);
                    }
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    if(addTaskCallback != null){
                        addTaskCallback.onFailure(call, e);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void deleteTask(ToDo toDo, @Nullable RequestHandler.RequestCallback callback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", toDo.getId());

            RequestHandler.delete(false, "/todo/del", jsonObject,
                    new RequestHandler.RequestCallback() {
                        @Override
                        public void onResponseSucceed(@NonNull Response response) {
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

    public static void updateTask(ToDo toDo, String newTask, @Nullable RequestHandler.RequestCallback callback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("task", newTask);
            RequestHandler.put(false, String.format("/todo/%s/task", toDo.getId()), jsonObject,
                    new RequestHandler.RequestCallback() {
                        @Override
                        public void onResponseSucceed(@NonNull Response response) {
                            if(callback !=null){
                                callback.onResponseSucceed(response);
                            }
                        }

                        @Override
                        public void onResponseFailure(@NonNull Response response) {
                            if(callback !=null){
                                callback.onResponseFailure(response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            if(callback !=null){
                                callback.onFailure(call, e);
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatus(ToDo toDo, boolean newStatus, @Nullable RequestHandler.RequestCallback callback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", newStatus);
            RequestHandler.put(false, String.format("/todo/%s/status", toDo.getId()), jsonObject,
                    new RequestHandler.RequestCallback() {
                        @Override
                        public void onResponseSucceed(@NonNull Response response) {
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
                                callback.onFailure(call , e);
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
