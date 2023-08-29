package com.example.todolist.domain.Todo.service;

import com.example.todolist.auth.MyUserDetails;
import com.example.todolist.domain.Todo.dto.TodoDTO;
import com.example.todolist.domain.Todo.entity.Todo;
import com.example.todolist.domain.User.entity.User;
import com.example.todolist.exception.AppException;
import com.example.todolist.exception.Error;
import com.example.todolist.domain.Todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    public List<TodoDTO> getAll(MyUserDetails user){
        Optional<List<Todo>> todoList = todoRepository.findAllByUser_Email(user.getEmail());
        return todoList
                .map(todos -> todos
                        .stream()
                        .map(todo -> modelMapper.map(todo, TodoDTO.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public TodoDTO create(TodoDTO todoDTO, MyUserDetails user){
        Todo newTodo = modelMapper.map(todoDTO, Todo.class);
        newTodo.setUser(User.builder().id(user.getId()).build());

        Todo savedTodo = todoRepository.save(newTodo);
        return modelMapper.map(savedTodo, TodoDTO.class);
    }

    public void delete(TodoDTO.Delete todoDTO, MyUserDetails user){
        Todo todo = getTodoOfUser(todoDTO.getId(), user);
        todoRepository.delete(todo);
    }

    public TodoDTO updateTask(Long id ,TodoDTO todoDTO, MyUserDetails user){
        Todo todo = getTodoOfUser(id, user);
        todo.setTask(todoDTO.getTask());

        return modelMapper.map(todoRepository.save(todo), TodoDTO.class) ;
    }

    public TodoDTO updateStatus(Long id, TodoDTO.UpdateStatus updateStatus, MyUserDetails user){
        Todo todo = getTodoOfUser(id, user);
        todo.setStatus(updateStatus.getStatus());

        return modelMapper.map(todoRepository.save(todo), TodoDTO.class);
    }

    private Todo getTodoOfUser(Long id, MyUserDetails user){
        Optional<Todo> optionalTodo = todoRepository
                .findById(id)
                .filter(entity -> entity.getUser().getId().equals(user.getId()));

        if(optionalTodo.isEmpty()){
            throw new AppException(Error.INVALID_TODO);
        }

        return optionalTodo.get();
    }
}
