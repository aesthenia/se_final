package com.example.se_final.service;

import com.example.se_final.dto.ReviewDto;
import com.example.se_final.mapper.ReviewMapper;
import com.example.se_final.model.Movie;
import com.example.se_final.model.Review;
import com.example.se_final.model.User;
import com.example.se_final.repository.MovieRepository;
import com.example.se_final.repository.ReviewRepository;
import com.example.se_final.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewDto> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findAllByMovieId(movieId).stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public ReviewDto addReview(ReviewDto dto, String username) {
        // 1. Находим фильм
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // 2. Находим пользователя (используем репозиторий из твоего Midterm)
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // (Опционально) Можно проверить, не оставлял ли он уже отзыв
        // if (reviewRepository.existsByMovieAndUser...) -> throw Exception

        // 3. Создаем отзыв
        Review review = reviewMapper.toEntity(dto);
        review.setMovie(movie);
        review.setUser(user);

        review = reviewRepository.save(review);

        // 4. ПЕРЕСЧЕТ РЕЙТИНГА ФИЛЬМА (Бизнес-логика)
        updateMovieAverageRating(movie);

        return reviewMapper.toDto(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Movie movie = review.getMovie();

        reviewRepository.delete(review);

        // После удаления отзыва рейтинг тоже надо пересчитать!
        updateMovieAverageRating(movie);
    }

    // Вспомогательный метод для пересчета рейтинга
    private void updateMovieAverageRating(Movie movie) {
        // Получаем актуальный список отзывов из базы
        List<Review> reviews = reviewRepository.findAllByMovieId(movie.getId());

        if (reviews.isEmpty()) {
            movie.setAverageRating(0.0);
        } else {
            double average = reviews.stream()
                    .mapToInt(Review::getStars)
                    .average()
                    .orElse(0.0);

            // Округляем до 1 знака (например, 8.7)
            double roundedAverage = Math.round(average * 10.0) / 10.0;
            movie.setAverageRating(roundedAverage);
        }
        movieRepository.save(movie);
    }
}