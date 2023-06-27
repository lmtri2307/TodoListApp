package com.example.todolist.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Utils.RequestHandler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class ToDoModel implements Serializable, Cloneable {
    private String _id;
    private boolean status;
    private String task;


    @JsonCreator
    public ToDoModel(@JsonProperty("_id") String id,
                     @JsonProperty("status") boolean status,
                     @JsonProperty("task") String task) {
        this._id = id;
        this.status = status;
        this.task = task;
    }


    public boolean getStatus() {
        return this.status;
    }

    public String getId(){ return _id;}
    public String getTask() {
        return task;
    }

    public void toggle() {
        this.status = !this.status;
    }

    public void setTask(String newTask){
        this.task = newTask;
    }
    public void setTask(String newTask, @Nullable RequestHandler.RequestCallback callback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("task", newTask);
            RequestHandler.put(false, String.format("/todo/%s/task", _id), jsonObject,
                    new RequestHandler.RequestCallback() {
                @Override
                public void onResponseSucceed(@NonNull Response response) {
                    task = newTask;
                    Objects.requireNonNull(callback).onResponseSucceed(response);
                }

                @Override
                public void onResponseFailure(@NonNull Response response) {
                    Objects.requireNonNull(callback).onResponseFailure(response);
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Objects.requireNonNull(callback).onFailure(call, e);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setStatus(boolean newStatus, @Nullable RequestHandler.RequestCallback callback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", newStatus);
            RequestHandler.put(false, String.format("/todo/%s/status", _id), jsonObject,
                    new RequestHandler.RequestCallback() {
                        @Override
                        public void onResponseSucceed(@NonNull Response response) {
                            status = newStatus;
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


    @Override
    public ToDoModel clone() {
        try {
            ToDoModel clone = (ToDoModel) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
