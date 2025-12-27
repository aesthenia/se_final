package com.example.se_final.service;

import com.example.se_final.dto.ActorDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class ActorServiceTest {

    @Autowired
    private ActorService actorService;

    @Test
    void createActorTest() {
        ActorDto dto = new ActorDto();
        dto.setFullName("Brad Pitt");
        dto.setBirthDate(LocalDate.of(1963, 12, 18));

        ActorDto created = actorService.createActor(dto);

        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals("Brad Pitt", created.getFullName());
    }

    @Test
    void findByIdTest() {
        ActorDto dto = new ActorDto(null, "Johnny Depp", LocalDate.of(1963, 6, 9));
        ActorDto saved = actorService.createActor(dto);

        ActorDto found = actorService.findById(saved.getId());

        Assertions.assertNotNull(found);
        Assertions.assertEquals("Johnny Depp", found.getFullName());
    }

    @Test
    void findById_NotFound_Test() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            actorService.findById(-999L);
        });
    }

    @Test
    void findAllTest() {
        actorService.createActor(new ActorDto(null, "Actor 1", LocalDate.now()));
        actorService.createActor(new ActorDto(null, "Actor 2", LocalDate.now()));

        List<ActorDto> actors = actorService.findAll();

        Assertions.assertNotNull(actors);
        Assertions.assertTrue(actors.size() >= 2);
    }

    @Test
    void updateActorTest() {
        ActorDto saved = actorService.createActor(new ActorDto(null, "Old Name", LocalDate.now()));

        ActorDto updateDto = new ActorDto();
        updateDto.setFullName("New Name");
        updateDto.setBirthDate(LocalDate.of(1990, 1, 1));

        ActorDto updated = actorService.updateActor(saved.getId(), updateDto);

        Assertions.assertEquals("New Name", updated.getFullName());
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), updated.getBirthDate());
    }

    @Test
    void deleteActorTest() {
        ActorDto saved = actorService.createActor(new ActorDto(null, "To Be Deleted", LocalDate.now()));
        Long id = saved.getId();

        actorService.deleteActor(id);

        Assertions.assertThrows(RuntimeException.class, () -> {
            actorService.findById(id);
        });
    }
}