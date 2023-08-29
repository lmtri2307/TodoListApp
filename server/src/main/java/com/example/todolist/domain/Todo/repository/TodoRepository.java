package com.example.todolist.domain.Todo.repository;

import com.example.todolist.domain.Todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<List<Todo>> findAllByUser_Email(String user_email);
}
