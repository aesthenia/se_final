package com.example.se_final.mapper;

import com.example.se_final.dto.ReviewDto;
import com.example.se_final.model.Movie;
import com.example.se_final.model.Review;
import com.example.se_final.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    void toDtoTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("reviewer_nick");

        Movie movie = new Movie();
        movie.setId(10L);

        Review review = new Review();
        review.setId(100L);
        review.setStars(8);
        review.setComment("Great movie!");
        review.setPostedAt(LocalDateTime.now());
        review.setUser(user);
        review.setMovie(movie);

        ReviewDto dto = reviewMapper.toDto(review);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(8, dto.getStars());
        Assertions.assertEquals("reviewer_nick", dto.getUsername());
        Assertions.assertEquals(1L, dto.getUserId());
        Assertions.assertEquals(10L, dto.getMovieId());
    }

    @Test
    void toEntityTest() {
        ReviewDto dto = new ReviewDto();
        dto.setStars(5);
        dto.setComment("Average");
        dto.setMovieId(10L);

        Review review = reviewMapper.toEntity(dto);

        Assertions.assertNotNull(review);
        Assertions.assertEquals(5, review.getStars());
        Assertions.assertEquals("Average", review.getComment());
        Assertions.assertNull(review.getMovie());
        Assertions.assertNull(review.getUser());
    }
}