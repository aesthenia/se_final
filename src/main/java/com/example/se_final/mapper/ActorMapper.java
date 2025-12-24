package com.example.se_final.mapper;

import com.example.se_final.dto.ActorDto;
import com.example.se_final.model.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    ActorDto toDto(Actor actor);

    Actor toEntity(ActorDto dto);
}