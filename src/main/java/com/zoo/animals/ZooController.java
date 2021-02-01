package com.zoo.animals;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}