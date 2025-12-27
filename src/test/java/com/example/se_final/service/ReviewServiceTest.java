package com.example.se_final.service;

import com.example.se_final.dto.MovieDto;
import com.example.se_final.dto.ReviewDto;
import com.example.se_final.dto.UserDto;
import com.example.se_final.model.Permission;
import com.example.se_final.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionRepository permissionRepository;

    private Long testMovieId;
    private final String testUsername = "review_user";

    @BeforeEach
    void setUp() {
        if (permissionRepository.findByName("ROLE_USER") == null) {
            permissionRepository.save(new Permission(null, "ROLE_USER"));
        }

        userService.registerUser(new UserDto(null, testUsername, "test@mail.com", "password"));

        MovieDto movie = movieService.createMovie(new MovieDto(null, "Rating Test Movie", 2024, 120, "Desc", 0.0, null, null, null));
        testMovieId = movie.getId();
    }

    @Test
    void addReviewAndCalculateRatingTest() {
        ReviewDto review1 = new ReviewDto();
        review1.setMovieId(testMovieId);
        review1.setStars(10);
        review1.setComment("Masterpiece!");
        reviewService.addReview(review1, testUsername);

        MovieDto movieAfterFirst = movieService.findById(testMovieId);
        Assertions.assertEquals(10.0, movieAfterFirst.getAverageRating());

        ReviewDto review2 = new ReviewDto();
        review2.setMovieId(testMovieId);
        review2.setStars(4);
        review2.setComment("Not bad");
        reviewService.addReview(review2, testUsername);

        MovieDto movieAfterSecond = movieService.findById(testMovieId);
        Assertions.assertEquals(7.0, movieAfterSecond.getAverageRating());
    }

    @Test
    void deleteReviewAndRecalculateRatingTest() {
        ReviewDto r1 = reviewService.addReview(new ReviewDto(null, 8, "Good", null, testMovieId, null, null), testUsername);
        ReviewDto r2 = reviewService.addReview(new ReviewDto(null, 10, "Best", null, testMovieId, null, null), testUsername);

        Assertions.assertEquals(9.0, movieService.findById(testMovieId).getAverageRating());

        reviewService.deleteReview(r2.getId());

        MovieDto movieAfterDelete = movieService.findById(testMovieId);
        Assertions.assertEquals(8.0, movieAfterDelete.getAverageRating());
    }

    @Test
    void getReviewsByMovieIdTest() {
        reviewService.addReview(new ReviewDto(null, 5, "C1", null, testMovieId, null, null), testUsername);
        reviewService.addReview(new ReviewDto(null, 7, "C2", null, testMovieId, null, null), testUsername);

        List<ReviewDto> reviews = reviewService.getReviewsByMovieId(testMovieId);

        Assertions.assertEquals(2, reviews.size());
    }
}