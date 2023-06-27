package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todolist.Data.ToDoList;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.RequestHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private ToDoModel toDoModel = null;
    private Callback callback = null;

    private EditText newTaskText;
    private Button newTaskSaveButton;


    public AddNewTask(ToDoModel toDoModel){
        this.toDoModel = toDoModel;
    }
    public AddNewTask(ToDoModel toDoModel, Callback callback){
        this.toDoModel = toDoModel.clone();
        this.callback = callback;
    }
    public AddNewTask(Callback callback){
        this.toDoModel = new ToDoModel("",false,"");
        this.callback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = view.findViewById(R.id.newTaskText);
        newTaskSaveButton= view.findViewById(R.id.newTaskButton);

        newTaskText.setText(toDoModel.getTask());
        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setAlpha(0.5F);
                } else {
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDoModel.setTask(newTaskText.getText().toString());
                callback.saveClickCallback(toDoModel);
                dismiss();
            }
        });
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        callback.dismissCallback();
        super.onCancel(dialog);
    }


    public interface Callback {
        public void saveClickCallback(ToDoModel toDoModel);
        public void dismissCallback();
    }
}
