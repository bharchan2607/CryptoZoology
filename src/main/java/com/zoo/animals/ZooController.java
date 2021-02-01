package com.zoo.animals;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zoo/animals")
public class ZooController {

    private ZooService zooService;

    public ZooController(ZooService zooService) {
        this.zooService = zooService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDTO addAnimals(@RequestBody AnimalDTO animal){
        return zooService.addAnimal(animal);
    }

    @GetMapping
    public List<AnimalDTO> getAllAnimals(){
        return zooService.getAllAnimals();
    }

    @GetMapping("/feed/{animalId}")
    public void feedAnimals(@PathVariable Long animalId){
        zooService.feedAnimals(animalId);
    }

    @PostMapping("/place/{animalId}")
    public AnimalDTO placeAnimalsInHabitat(
            @PathVariable Long animalId,
            @RequestBody AnimalDTO animal){
        return zooService.placeAnimalsInHabitat(animalId,
                animal);
    }

}
