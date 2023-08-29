package com.example.todolist.domain.User.service;

import com.example.todolist.domain.User.dto.UserDTO;

public interface UserService {
    public UserDTO register(UserDTO.AuthRequest authRequestDTO);
    public UserDTO login(UserDTO.AuthRequest authRequestDTO);
}
