package com.example.sakilademo.actors;

import com.example.sakilademo.utility.Utils;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.server.ResponseStatusException;



import java.util.List;

@Service
@Transactional

public class ActorService {
    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public ActorResponse createActor(ActorInput input) {
        Actor a = new Actor(input);
        return new ActorResponse(actorRepository.save(a));
    }

    public List<ActorResponse> getAllActors() {
        return actorRepository.findAll().stream().map(ActorResponse::new).toList();
    }

    public ActorResponse getOneActor(short id) {
        Actor actor = actorRepository.findById(id);
        if (actor != null) {
            return new ActorResponse(actorRepository.findById(id));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public ActorResponse updateActor(ActorInput input, short id) {
        Actor actor = actorRepository.findById(id);
        if (actor != null ) {
            Utils.copyNonNullProperties(input, actor);
            return new ActorResponse(actorRepository.save(actor));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteActor(short id) {
        if (actorRepository.findById(id) != null) {
            actorRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


}
