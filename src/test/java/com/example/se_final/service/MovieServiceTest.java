package com.example.se_final.service;

import com.example.se_final.dto.ActorDto;
import com.example.se_final.dto.MovieDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorService actorService;

    @Test
    void createMovieWithActorsTest() {
        ActorDto actor1 = actorService.createActor(new ActorDto(null, "Leonardo DiCaprio", LocalDate.now()));
        ActorDto actor2 = actorService.createActor(new ActorDto(null, "Cillian Murphy", LocalDate.now()));

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Inception");
        movieDto.setReleaseYear(2010);
        movieDto.setDurationMinutes(148);
        movieDto.setActorIds(List.of(actor1.getId(), actor2.getId()));

        MovieDto savedMovie = movieService.createMovie(movieDto);

        Assertions.assertNotNull(savedMovie.getId());
        Assertions.assertEquals("Inception", savedMovie.getTitle());
        Assertions.assertEquals(2, savedMovie.getActorIds().size());
    }

    @Test
    void findByIdTest() {
        MovieDto saved = movieService.createMovie(new MovieDto(null, "The Matrix", 1999, 136, "Desc", 0.0, null, null, null));

        MovieDto found = movieService.findById(saved.getId());

        Assertions.assertNotNull(found);
        Assertions.assertEquals("The Matrix", found.getTitle());
    }

    @Test
    void searchByTitleTest() {
        movieService.createMovie(new MovieDto(null, "Batman Begins", 2005, 140, "Desc", 0.0, null, null, null));
        movieService.createMovie(new MovieDto(null, "The Dark Knight", 2008, 152, "Desc", 0.0, null, null, null));

        List<MovieDto> results = movieService.searchByTitle("Knight");

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals("The Dark Knight", results.get(0).getTitle());
    }

    @Test
    void updateMovieTest() {
        MovieDto saved = movieService.createMovie(new MovieDto(null, "Old Title", 2000, 100, "Old Desc", 0.0, null, null, null));

        MovieDto updateDto = new MovieDto();
        updateDto.setTitle("New Title");
        updateDto.setDescription("New Description");
        updateDto.setReleaseYear(2021);

        MovieDto updated = movieService.updateMovie(saved.getId(), updateDto);

        Assertions.assertEquals("New Title", updated.getTitle());
        Assertions.assertEquals("New Description", updated.getDescription());
        Assertions.assertEquals(2021, updated.getReleaseYear());
    }

    @Test
    void deleteMovieTest() {
        MovieDto saved = movieService.createMovie(new MovieDto(null, "To Delete", 2022, 90, "...", 0.0, null, null, null));
        Long id = saved.getId();

        movieService.deleteMovie(id);

        Assertions.assertThrows(RuntimeException.class, () -> movieService.findById(id));
    }
}