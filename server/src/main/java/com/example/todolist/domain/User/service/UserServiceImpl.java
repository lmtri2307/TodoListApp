package com.example.todolist.domain.User.service;

import com.example.todolist.domain.User.dto.UserDTO;
import com.example.todolist.domain.User.entity.User;
import com.example.todolist.exception.AppException;
import com.example.todolist.exception.Error;
import com.example.todolist.domain.User.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private ModelMapper mapper;
    private PasswordEncoder encoder;

    public UserDTO register(UserDTO.AuthRequest authRequestDTO){
        validateUserRegistration(authRequestDTO);

        User newUser = mapper.map(authRequestDTO, User.class);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepository.save(newUser);

        return mapper.map(newUser, UserDTO.class);
    }

    public UserDTO login(UserDTO.AuthRequest authRequestDTO){
        Optional<User> optionalUser = userRepository.findByEmail(authRequestDTO.getEmail());

        if(optionalUser.isEmpty()){
            throw new AppException(Error.EMAIL_NOT_FOUND);
        }

        User user = optionalUser.get();

        boolean checkPassword = encoder.matches(authRequestDTO.getPassword(),user.getPassword());
        if(!checkPassword){
            throw new AppException(Error.WRONG_PASSWORD);
        }

        return mapper.map(user, UserDTO.class);
    }

    private void validateUserRegistration(UserDTO.AuthRequest authRequestDTO){
        Optional<User> user = userRepository.findByEmail(authRequestDTO.getEmail());
        if(user.isPresent()){
            throw new AppException(Error.EMAIL_DUPLICATED);
        }
    }
}
