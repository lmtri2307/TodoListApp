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

public class ToDo implements Serializable, Cloneable {
    private String _id;
    private boolean status;
    private String task;


    @JsonCreator
    public ToDo(@JsonProperty("id") String id,
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

    public void setTask(String newTask){
        this.task = newTask;
    }

    public void setStatus(boolean newStatus){
        this.status = newStatus;
    }

    @Override
    public ToDo clone() {
        try {
            ToDo clone = (ToDo) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
