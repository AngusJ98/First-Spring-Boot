package com.example.sakilademo.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(ActorInput input) {
        Actor a = new Actor(input);
        actorRepository.save(a);
        return a;
    }
}
