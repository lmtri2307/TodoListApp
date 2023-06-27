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

import com.example.todolist.Model.User;
import com.example.todolist.Utils.RequestHandler;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public class SignupFragment extends Fragment {



    public SignupFragment() {
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        Button signupBtn = view.findViewById(R.id.buttonLogIn2);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.signupToLogin);
            }
        });
        Button loginBtn = view.findViewById(R.id.buttonSignUp2);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText)view.findViewById(R.id.editTextUserName2)).getText().toString();
                String password = ((EditText)view.findViewById(R.id.editTextPassword2)).getText().toString();
                String retypePassword = ((EditText)view.findViewById(R.id.editTextRetypePassword)).getText().toString();
                if(!password.equals(retypePassword)){
                    Toast.makeText(getContext(),"Retype Password not match",Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    User.signup(username, password,
                            new RequestHandler.RequestCallback() {
                                @Override
                                public void onResponseSucceed(@NonNull Response response) {
                                    Handler mainHandler = new Handler(Looper.getMainLooper());
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Navigation.findNavController(view).navigate(R.id.signupToHome);
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
                                                    "Sign up failed",
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
                                                    "Sign up failed",
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