package com.example.se_final.mapper;

import com.example.se_final.dto.MovieDto;
import com.example.se_final.model.Actor;
import com.example.se_final.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MovieMapperTest {

    @Autowired
    private MovieMapper movieMapper;

    @Test
    void toDtoTest() {
        Actor actor1 = new Actor(); actor1.setId(101L);
        Actor actor2 = new Actor(); actor2.setId(102L);

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setAverageRating(8.8);
        movie.setActors(List.of(actor1, actor2));

        MovieDto dto = movieMapper.toDto(movie);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Inception", dto.getTitle());
        Assertions.assertNotNull(dto.getActorIds());
        Assertions.assertEquals(2, dto.getActorIds().size());
        Assertions.assertTrue(dto.getActorIds().contains(101L));
        Assertions.assertTrue(dto.getActorIds().contains(102L));
    }

    @Test
    void toEntityTest() {
        MovieDto dto = new MovieDto();
        dto.setTitle("Interstellar");
        dto.setReleaseYear(2014);

        Movie movie = movieMapper.toEntity(dto);

        Assertions.assertNotNull(movie);
        Assertions.assertEquals("Interstellar", movie.getTitle());
        Assertions.assertEquals(2014, movie.getReleaseYear());
        Assertions.assertNull(movie.getActors());
        Assertions.assertNull(movie.getReviews());
    }
}