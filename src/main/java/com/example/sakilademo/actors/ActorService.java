package com.example.sakilademo.actors;

import lombok.AllArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;


    public ActorResponse createActor(ActorInput input) {
        Actor a = new Actor(input);
        return new ActorResponse(a);
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
            BeanUtils.copyProperties(input, actor);
            return new ActorResponse(actorRepository.save(actor));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> deleteActor(short id) {
        if (actorRepository.findById(id) != null) {
            actorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


}
