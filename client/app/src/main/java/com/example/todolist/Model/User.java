package com.example.todolist.Model;

import androidx.annotation.NonNull;

import com.example.todolist.Utils.Converter;
import com.example.todolist.Utils.RequestHandler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class User {
    public static User instance;
    String username;

    @JsonCreator
    public User(@JsonProperty("email") String username) {
        this.username = username;
    }
}
