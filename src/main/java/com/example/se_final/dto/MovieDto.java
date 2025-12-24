package com.example.se_final.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;

    private String title;

    private Integer releaseYear;

    private Integer durationMinutes;

    private String description;

    private Double averageRating;

    private List<Long> actorIds;

    private List<ActorDto> actors;

    private List<ReviewDto> reviews;
}