package com.example.movies_api.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentDto {
    private Long id;
    @NotBlank
    private String content;

    private Long userId;
}
