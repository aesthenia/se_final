package com.example.se_final.mapper;

import com.example.se_final.dto.MovieDto;
import com.example.se_final.model.Actor;
import com.example.se_final.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ActorMapper.class, ReviewMapper.class})
public interface MovieMapper {

    @Mapping(target = "actorIds", expression = "java(mapActorsToIds(movie.getActors()))")
    MovieDto toDto(Movie movie);

    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    Movie toEntity(MovieDto dto);

    default List<Long> mapActorsToIds(List<Actor> actors) {
        if (actors == null) return null;
        return actors.stream()
                .map(Actor::getId)
                .toList();
    }
}