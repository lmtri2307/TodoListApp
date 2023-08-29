package com.example.todolist.domain.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthRequest{
        @NotNull
        @NotBlank
        private String email;

        @NotNull
        @NotBlank
        private String password;
    }
}
