package com.zoo.animals;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZooService {

    ZooRepository zooRepository;

    public ZooService(ZooRepository zooRepository) {
        this.zooRepository = zooRepository;
    }

    public AnimalDTO addAnimal(AnimalDTO animal) {
        AnimalEntity animalEntity =  zooRepository.save(new AnimalEntity(animal.getName(), animal.getType()));
        return mapToDTO(animalEntity);
    }

    public List<AnimalDTO> getAllAnimals(){
        return zooRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AnimalDTO mapToDTO(AnimalEntity animalEntity) {
       return new AnimalDTO(animalEntity.getName(), animalEntity.getType());
    }
}
