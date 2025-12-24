package com.example.se_final.mapper;

import com.example.se_final.dto.ReviewDto;
import com.example.se_final.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    ReviewDto toDto(Review review);

    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    Review toEntity(ReviewDto dto);
}