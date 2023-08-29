package com.example.todolist.domain.Todo.service;

import com.example.todolist.auth.MyUserDetails;
import com.example.todolist.domain.Todo.dto.TodoDTO;
import com.example.todolist.domain.User.dto.UserDTO;

import java.util.List;

public interface TodoService {
    public List<TodoDTO> getAll(MyUserDetails user);
    public TodoDTO create(TodoDTO todoDTO, MyUserDetails user);
    public void delete(TodoDTO.Delete todoDTO, MyUserDetails user);
    public TodoDTO updateTask(Long id ,TodoDTO todoDTO, MyUserDetails user);
    public TodoDTO updateStatus(Long id, TodoDTO.UpdateStatus updateStatus, MyUserDetails user);

}
