package com.example.se_final.service;

import com.example.se_final.dto.MovieDto;
import com.example.se_final.mapper.MovieMapper;
import com.example.se_final.model.Actor;
import com.example.se_final.model.Movie;
import com.example.se_final.repository.ActorRepository;
import com.example.se_final.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieMapper movieMapper;

    public List<MovieDto> findAll() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .toList();
    }

    public MovieDto findById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return movieMapper.toDto(movie);
    }

    public List<MovieDto> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(movieMapper::toDto)
                .toList();
    }

    @Transactional
    public MovieDto createMovie(MovieDto dto) {
        Movie movie = movieMapper.toEntity(dto);

        if (dto.getActorIds() != null && !dto.getActorIds().isEmpty()) {
            List<Actor> actors = actorRepository.findAllById(dto.getActorIds());
            movie.setActors(actors);
        }

        return movieMapper.toDto(movieRepository.save(movie));
    }

    @Transactional
    public MovieDto updateMovie(Long id, MovieDto dto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDurationMinutes(dto.getDurationMinutes());

        if (dto.getActorIds() != null) {
            List<Actor> actors = actorRepository.findAllById(dto.getActorIds());
            movie.setActors(actors);
        }

        return movieMapper.toDto(movieRepository.save(movie));
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}