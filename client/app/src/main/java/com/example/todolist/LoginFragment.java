package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.API.UserAPI;
import com.example.todolist.Model.User;
import com.example.todolist.Utils.RequestHandler;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button signupBtn = view.findViewById(R.id.buttonSignUp);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.loginToSignup);
            }
        });

        Button loginBtn = view.findViewById(R.id.buttonLogIn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText)view.findViewById(R.id.editTextUsername)).getText().toString();
                String password = ((EditText)view.findViewById(R.id.editTextPassword)).getText().toString();

                try {
                    UserAPI.login(username, password,
                            new RequestHandler.RequestCallback() {
                                @Override
                                public void onResponseSucceed(@NonNull Response response) {
                                    Handler mainHandler = new Handler(Looper.getMainLooper());
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Navigation.findNavController(view).navigate(R.id.loginToHome);
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
                                                    "Login failed",
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
                                                    "Login failed",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;

    }
}