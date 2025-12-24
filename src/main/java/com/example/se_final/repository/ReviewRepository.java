package com.example.se_final.repository;

import com.example.se_final.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByMovieId(Long movieId);

    List<Review> findAllByUserId(Long userId);
}