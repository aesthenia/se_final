package com.example.se_final.service;

import com.example.se_final.dto.ActorDto;
import com.example.se_final.mapper.ActorMapper;
import com.example.se_final.model.Actor;
import com.example.se_final.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public List<ActorDto> findAll() {
        return actorRepository.findAll().stream()
                .map(actorMapper::toDto)
                .toList();
    }

    public ActorDto findById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found with id: " + id));
        return actorMapper.toDto(actor);
    }

    public ActorDto createActor(ActorDto dto) {
        Actor actor = actorMapper.toEntity(dto);
        return actorMapper.toDto(actorRepository.save(actor));
    }

    public ActorDto updateActor(Long id, ActorDto dto) {
        Actor existingActor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found"));

        existingActor.setFullName(dto.getFullName());
        existingActor.setBirthDate(dto.getBirthDate());

        return actorMapper.toDto(actorRepository.save(existingActor));
    }

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }
}