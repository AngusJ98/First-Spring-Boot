package com.example.sakilademo.actors;

import com.example.sakilademo.validation.ValidationGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActorController {

    @Autowired
    private ActorService actorService;


    @GetMapping("/greeting")
    public String sayHi() {
        return "Hello there friend";
    }

    @GetMapping("/actor/{id}")
    public ResponseEntity<ActorResponse> getActorById(@PathVariable short id){
        return (actorService.getOneActor(id));
    }

    @GetMapping("/actor/")
    public ResponseEntity<List<ActorResponse>> getActors() {
        return actorService.getAllActors();
    }

    @DeleteMapping("/actor/{id}")
    public ResponseEntity<HttpStatus> deleteActor(@PathVariable short id) {
        return actorService.deleteActor(id);
    }

    @PutMapping("/actor/{id}")
    public  ResponseEntity<ActorResponse> updateActor(@PathVariable short id, @Validated(ValidationGroup.Create.class) @RequestBody ActorInput input) {
        return actorService.updateActor(input, id);
    }

    @PostMapping("/actor/")
    public ResponseEntity<ActorResponse> newActor(@RequestBody @Validated(ValidationGroup.Create.class) ActorInput input) {
        return actorService.createActor(input);
    }

    @PatchMapping("/actor/{id}")
    public  ResponseEntity<ActorResponse> patchActor(@PathVariable short id, @RequestBody @Validated(ValidationGroup.Update.class) ActorInput newData) {
        return actorService.updateActor(newData, id);
    }


}
