package com.example.sakilademo.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Actor getActorById(@PathVariable short id){
        return (actorRepository.findById(id));
    }

    @GetMapping("/actor/")
    public List<Actor> getActors() {
        return actorRepository.findAll();
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
        Actor exist = actorRepository.findById(id);
        if (exist != null) {
            exist.setFirstName(actor.getFirstName());
            exist.setLastName(actor.getLastName());
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
                switch (a) {
                    case "firstName":
                        actor.setFirstName(b);
                        break;
                    case "lastName":
                        actor.setLastName(b);
                        break;
                }
            });

            return ResponseEntity.ok(actorRepository.save(actor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
