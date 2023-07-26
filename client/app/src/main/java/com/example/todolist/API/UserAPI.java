package com.example.todolist.API;

import androidx.annotation.NonNull;

import com.example.todolist.Model.User;
import com.example.todolist.Utils.Converter;
import com.example.todolist.Utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class UserAPI {
    public static void login(String username, String password,
                             RequestHandler.RequestCallback callback) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        RequestHandler.post(false,"/auth/login", jsonObject, new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed(@NonNull Response response) {
                try {
                    User.instance = Converter.fromJsonString(response.body().string(), User.class);
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
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        RequestHandler.post(false, "/auth/register", jsonObject, new RequestHandler.RequestCallback() {
            @Override
            public void onResponseSucceed( @NonNull Response response) {
                try {
                    User.instance = Converter.fromJsonString(
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
