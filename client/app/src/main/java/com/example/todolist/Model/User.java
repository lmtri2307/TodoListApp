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
    String _id;
    String username;


    @JsonCreator
    public User(@JsonProperty("_id") String _id, @JsonProperty("username") String username) {
        this._id = _id;
        this.username = username;
    }



    public static void login(String username, String password,
                                RequestHandler.RequestCallback callback) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        RequestHandler.post(false,"/auth/login", jsonObject, new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed(@NonNull Response response) {
                try {
                    instance = Converter.fromJsonString(response.body().string(), User.class);
                    callback.onResponseSucceed(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseFailure(@NonNull Response response) {
                callback.onResponseFailure(response);
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(call, e);
            }
        });
    }

    public static void signup(String username, String password,
                                 RequestHandler.RequestCallback callback) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username
        );
        jsonObject.put("password",
                password);
        RequestHandler.post(false, "/auth/register", jsonObject, new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed( @NonNull Response response) {
                try {
                    instance = Converter.fromJsonString(
                            Objects.requireNonNull(response.body()).string(),
                            User.class);
                    callback.onResponseSucceed(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseFailure( @NonNull Response response) {
                callback.onResponseFailure(response);
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(call, e);
            }
        });
    }
}
