package com.example.sakilademo.actors;

import com.example.sakilademo.validation.ValidationGroup;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/actors")
@RestController
public class ActorController {

    @Autowired
    private ActorService actorService;


    @GetMapping("/{id}")
    public ActorResponse getActorById(@PathVariable short id){
        return (actorService.getOneActor(id));
    }

    @GetMapping
    public List<ActorResponse> getActors() {
        return actorService.getAllActors();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActor(@PathVariable short id) {
        return actorService.deleteActor(id);
    }

    @PutMapping("/{id}")
    public  ActorResponse updateActor(@PathVariable short id, @Validated(ValidationGroup.Create.class) @RequestBody ActorInput input) {
        return actorService.updateActor(input, id);
    }

    @PostMapping
    public ActorResponse newActor(@RequestBody @Validated(ValidationGroup.Create.class) ActorInput input) {
        return actorService.createActor(input);
    }

    @PatchMapping("/{id}")
    public  ActorResponse patchActor(@PathVariable short id, @RequestBody @Validated(ValidationGroup.Update.class) ActorInput newData) {
        return actorService.updateActor(newData, id);
    }


}
