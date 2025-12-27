package com.example.se_final.mapper;

import com.example.se_final.dto.ActorDto;
import com.example.se_final.model.Actor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class ActorMapperTest {

    @Autowired
    private ActorMapper actorMapper;

    @Test
    void toDtoTest() {
        Actor actor = new Actor();
        actor.setId(1L);
        actor.setFullName("Tom Hanks");
        actor.setBirthDate(LocalDate.of(1956, 7, 9));

        ActorDto dto = actorMapper.toDto(actor);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(actor.getId(), dto.getId());
        Assertions.assertEquals(actor.getFullName(), dto.getFullName());
        Assertions.assertEquals(actor.getBirthDate(), dto.getBirthDate());
    }

    @Test
    void toEntityTest() {
        ActorDto dto = new ActorDto(10L, "Leonardo DiCaprio", LocalDate.of(1974, 11, 11));

        Actor actor = actorMapper.toEntity(dto);

        Assertions.assertNotNull(actor);
        Assertions.assertEquals(dto.getId(), actor.getId());
        Assertions.assertEquals(dto.getFullName(), actor.getFullName());
        Assertions.assertEquals(dto.getBirthDate(), actor.getBirthDate());
    }
}