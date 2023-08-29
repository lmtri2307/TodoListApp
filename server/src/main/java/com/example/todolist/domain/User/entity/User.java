package com.example.todolist.domain.User.entity;

import com.example.todolist.domain.Todo.entity.Todo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Collection<Todo> todos;
}
