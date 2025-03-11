package com.example.movies_api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {
    private int callCount;
    private LocalDateTime lastCallTime;
}