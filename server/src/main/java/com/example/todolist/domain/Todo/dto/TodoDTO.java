package com.example.todolist.domain.Todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    private Long id;

    @NotNull
    private String task;

    private Boolean status;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Delete{
        @NotNull
        private Long id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateStatus{
        @NotNull
        private Boolean status;
    }
}
