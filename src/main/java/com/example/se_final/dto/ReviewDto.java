package com.example.se_final.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;

    @Min(value = 1, message = "Min rating is 1")
    @Max(value = 10, message = "Max rating is 10")
    private Integer stars;

    private String comment;

    private LocalDateTime postedAt;

    private Long movieId;

    private Long userId;
    private String username;
}