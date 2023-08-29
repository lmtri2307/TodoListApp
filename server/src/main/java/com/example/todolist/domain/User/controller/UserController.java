package com.example.todolist.domain.User.controller;

import com.example.todolist.domain.User.dto.UserDTO;
import com.example.todolist.domain.User.service.UserService;
import com.example.todolist.domain.User.service.UserServiceImpl;
import com.example.todolist.utils.JWTUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth")

public class UserController {
    private UserService authService;
    private JWTUtils jwtUtils;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO register(@RequestBody @Valid UserDTO.AuthRequest registerDTO, HttpServletResponse res){
        UserDTO userDTO = authService.register(registerDTO);

        String token = jwtUtils.generateToken(userDTO);
        setJWTToken(token, res);
        return userDTO;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO login(@RequestBody @Valid UserDTO.AuthRequest loginDTO, HttpServletResponse res){
        UserDTO userDTO = authService.login(loginDTO);
        String token = jwtUtils.generateToken(userDTO);

        setJWTToken(token, res);
        return userDTO;
    }

    private void setJWTToken(String token, HttpServletResponse res){
        Cookie cookie = new Cookie("jwt", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
    }
}
