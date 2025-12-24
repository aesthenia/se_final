package com.example.se_final.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {
    private Long id;

    private String fullName;

    private LocalDate birthDate;
}