package com.example.sakilademo.actors;

import org.hibernate.query.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;


    public ResponseEntity<Actor> createActor(ActorInput input) {
        Actor a = new Actor(input);
        return ResponseEntity.ok(a);
    }

    public ResponseEntity<List<ActorResponse>> getAllActors() {
        return ResponseEntity.ok(actorRepository.findAll().stream().map(ActorResponse::new).toList());
    }

    public ResponseEntity<ActorResponse> getOneActor(short id) {
        Actor actor = actorRepository.findById(id);
        if (actor != null) {
            return ResponseEntity.ok(new ActorResponse(actorRepository.findById(id)));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<Actor> updateActor(ActorInput input, short id) {
        Actor actor = actorRepository.findById(id);
        if (actor != null ) {
            BeanUtils.copyProperties(input, actor);
            return ResponseEntity.ok(actorRepository.save(actor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<HttpStatus> deleteActor(short id) {
        if (actorRepository.findById(id) != null) {
            actorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
