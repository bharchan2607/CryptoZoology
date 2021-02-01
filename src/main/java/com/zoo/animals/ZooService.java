package com.zoo.animals;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZooService {

    ZooRepository zooRepository;
    ZooHabitatRepository zooHabitatRepository;

    public ZooService(ZooRepository zooRepository, ZooHabitatRepository zooHabitatRepository) {

        this.zooRepository = zooRepository;
        this.zooHabitatRepository = zooHabitatRepository;
    }

    public AnimalDTO addAnimal(AnimalDTO animal) {
        AnimalEntity animalEntity =  zooRepository.save(new AnimalEntity(animal.getName(),
                animal.getType(), animal.getTreat()));
        return mapToDTO(animalEntity);
    }

    public List<AnimalDTO> getAllAnimals(){
        return zooRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void feedAnimals(Long animalId){
        AnimalEntity animal = zooRepository.findById(animalId).get();
        animal.setMood(true);
        zooRepository.save(animal);
    }

    public AnimalDTO placeAnimalsInHabitat(Long animalId, AnimalDTO animal){
        AnimalEntity animalEntity = zooRepository.findById(animalId).get();
        AnimalHabitatEntity habitat = zooHabitatRepository.findByType(animalEntity.getType());
        if(animal.getHabitat().equals(habitat.getHabitat())){
            animalEntity.setMood(true);
            animalEntity.setHabitat(animal.getHabitat());
        }else{
            animalEntity.setMood(false);
            animalEntity.setHabitat(animal.getHabitat());
        }
        return mapToDTO(zooRepository.save(animalEntity));
    }

    private AnimalDTO mapToDTO(AnimalEntity animalEntity) {
       AnimalDTO animal = new AnimalDTO(animalEntity.getName(),
               animalEntity.getType()
       , animalEntity.getMood());
       animal.setHabitat(animalEntity.getHabitat());
       return animal;
    }
}
