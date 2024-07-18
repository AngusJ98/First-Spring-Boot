package com.example.sakilademo.actors;

import java.util.Optional.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;


    public List<Actor> findByFirstName(String firstName) {
        return actorRepository.findAllByFirstNameContainsIgnoreCase(firstName);
    }

    @GetMapping("/greeting")
    public String sayHi() {
        return "Hello there friend";
    }

    @GetMapping("/actor/{id}")
    public ActorResponse getActorById(@PathVariable short id){
        return (new ActorResponse(actorRepository.findById(id)));
    }

    @GetMapping("/actor/")
    public List<ActorResponse> getActors() {
        return actorRepository.findAll().stream().map(ActorResponse::new).toList();
    }

    @DeleteMapping("/actor/{id}")
    public ResponseEntity<HttpStatus> deleteActor(@PathVariable short id) {
        try {
           actorRepository.deleteById(id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actor/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable short id, @RequestBody Actor actor) {
        actor.setId(id);
        Actor exist = actorRepository.findById(id);
        if (exist != null) {
            BeanUtils.copyProperties(actor, exist);
            return ResponseEntity.ok(actorRepository.save(exist));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/actor/")
    public ResponseEntity<Actor> newActor(@RequestBody Actor actor) {
        return ResponseEntity.ok(actorRepository.save(actor));
    }

    @PatchMapping("/actor/{id}")
    public  ResponseEntity<Actor> patchActor(@PathVariable short id, @RequestBody Map<String, String> newData) {
        Actor actor = actorRepository.findById(id);
        if (actor != null) {
            newData.forEach((a, b) -> {
                try {
                    Field field = ReflectionUtils.findField(Actor.class, a);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, actor, b);
                } catch (Exception e){
                    System.out.println("Field not found: " + a);
                }
            });

            return ResponseEntity.ok(actorRepository.save(actor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
