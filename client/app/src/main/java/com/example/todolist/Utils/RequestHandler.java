package com.example.todolist.Utils;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestHandler {
    static OkHttpClient client = createClient();
    static TokenExpiredCallback tokenExpiredCallback = null;

//    private static final String serverUrl = "http://10.0.2.2:1234";
    private static final String serverUrl = "http://192.168.1.6:1234";
//

    private static OkHttpClient createClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(new ExpiredTokenInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static void setTokenExpiredCallback(TokenExpiredCallback callback){
        tokenExpiredCallback = callback;
    }

    private static void sendRequest(boolean isAsynchronous,Request request, RequestCallback callback){
        Call call = client.newCall(request);
        if(isAsynchronous){
            try {
                Response response = call.execute();
                callback.onResponse(call, response);
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(call, e);
            }
        } else {
            call.enqueue(callback);
        }
    }
    public static void post(boolean isAsynchronous, String to, JSONObject jsonObject,  RequestCallback callback){
        RequestBody requestJsonBody = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json")
        );
        Request request = new Request.Builder()
                .url(String.format("%s%s", serverUrl, to))
                .post(requestJsonBody)
                .build();
        sendRequest(isAsynchronous, request, callback);
    }

    public static void delete(boolean isAsynchronous, String to, JSONObject jsonObject,  RequestCallback callback){
        RequestBody requestJsonBody = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json")
        );
        Request request = new Request.Builder()
                .url(String.format("%s%s", serverUrl, to))
                .delete(requestJsonBody)
                .build();
        sendRequest(isAsynchronous, request, callback);
    }

    public static void put(boolean isAsynchronous, String to, JSONObject jsonObject,  RequestCallback callback){
        RequestBody requestJsonBody = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json")
        );
        Request request = new Request.Builder()
                .url(String.format("%s%s", serverUrl, to))
                .put(requestJsonBody)
                .build();
        sendRequest(isAsynchronous, request, callback);
    }

    public static void get(boolean isAsynchronous, String to, RequestCallback callback){

        Request getRequest = new Request.Builder()
                .url(String.format("%s%s", serverUrl, to))
                .build();
        sendRequest(isAsynchronous, getRequest, callback);
    }

    public interface RequestCallback extends okhttp3.Callback{
        void onResponseSucceed( @NonNull Response response);
        void onResponseFailure( @NonNull Response response);
        @Override
        default void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException{
            if(response.code() >= 200 && response.code() < 300){
                onResponseSucceed(response);
            } else {
                onResponseFailure(response);
            }
        }
    }

    public interface TokenExpiredCallback {
        void onTokenExpired();
    }

    static class ExpiredTokenInterceptor implements Interceptor{

        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if(response.code() == 403){
                if(tokenExpiredCallback != null)
                    tokenExpiredCallback.onTokenExpired();
            }

            return response;
        }
    }
}
