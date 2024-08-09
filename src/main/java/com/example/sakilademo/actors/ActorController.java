package com.example.sakilademo.actors;

import com.example.sakilademo.validation.ValidationGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/actors")
@RestController
public class ActorController {


    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorResponse> getActorById(@PathVariable short id){
        try {
            ActorResponse response = actorService.getOneActor(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    public ResponseEntity<List<ActorResponse>> getActors() {
        return new ResponseEntity<>(actorService.getAllActors(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActorResponse> deleteActor(@PathVariable short id) {
        try {
            actorService.deleteActor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ActorResponse> updateActor(@PathVariable short id, @Validated(ValidationGroup.Create.class) @RequestBody ActorInput input) {
        try {
            return new ResponseEntity<>(actorService.updateActor(input, id), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<ActorResponse> newActor(@RequestBody @Validated(ValidationGroup.Create.class) ActorInput input) {
        return new ResponseEntity<>(actorService.createActor(input), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public  ResponseEntity<ActorResponse> patchActor(@PathVariable short id, @RequestBody @Validated(ValidationGroup.Update.class) ActorInput newData) {
        try {
            return new ResponseEntity<>(actorService.updateActor(newData, id), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
