package com.example.todolist.domain.Todo.controller;

import com.example.todolist.auth.MyUserDetails;
import com.example.todolist.domain.Todo.dto.TodoDTO;
import com.example.todolist.domain.Todo.service.TodoService;
import com.example.todolist.domain.Todo.service.TodoServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/todo")
public class TodoController {
    TodoService todoService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDTO> getToDo(@AuthenticationPrincipal MyUserDetails userDetails){
        return todoService.getAll(userDetails);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public TodoDTO createTodo(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody @Valid TodoDTO todoDTO
    ){
        return todoService.create(todoDTO, userDetails);
    }

    @DeleteMapping("/del")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTodo(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody @Valid TodoDTO.Delete todoDTO
    ){
        todoService.delete(todoDTO, userDetails);
    }

    @PutMapping("/{todoId}/task")
    @ResponseStatus(HttpStatus.OK)
    public TodoDTO updateTask(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody @Valid TodoDTO todoDTO,
            @PathVariable Long todoId
    ){
        return todoService.updateTask(todoId, todoDTO, userDetails);
    }

    @PutMapping("/{todoId}/status")
    @ResponseStatus(HttpStatus.OK)
    public TodoDTO updateStatus(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody @Valid TodoDTO.UpdateStatus updateStatus,
            @PathVariable Long todoId
    ) {
        return todoService.updateStatus(todoId, updateStatus, userDetails);
    }
}
