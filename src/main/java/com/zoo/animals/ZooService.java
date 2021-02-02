package com.zoo.animals;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        AnimalEntity animalEntity =  zooRepository.save(
                new AnimalEntity(animal.getName(),
                animal.getType(), animal.getTreat(),animal.getHabitat()));
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
        animal.getAnimalInfo().setMood(true);
        zooRepository.save(animal);
    }

    public AnimalDTO placeAnimalsInHabitat(Long animalId, AnimalDTO animal){
        AnimalEntity animalEntity = zooRepository.findById(animalId).get();
        AnimalHabitatEntity habitat = zooHabitatRepository.findByType(animalEntity.getAnimalInfo().getType());
        if(animal.getHabitat().equals(habitat.getHabitat())){
            animalEntity.getAnimalInfo().setMood(true);
            animalEntity.getAnimalInfo().setHabitat(animal.getHabitat());
        }else{
            animalEntity.getAnimalInfo().setMood(false);
            animalEntity.getAnimalInfo().setHabitat(animal.getHabitat());
        }
        return mapToDTO(zooRepository.save(animalEntity));
    }

    private AnimalDTO mapToDTO(AnimalEntity animalEntity) {
       AnimalDTO animal = new AnimalDTO(animalEntity.getName(),
               animalEntity.getAnimalInfo().getType()
       , animalEntity.getAnimalInfo().isMood());
       animal.setHabitat(animalEntity.getAnimalInfo().getHabitat());
       return animal;
    }

    public List<AnimalDTO> searchAnimalsByMoodAndType(boolean mood, String type) {
        return zooRepository.findByMoodAndType(mood, type)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<String> searchForEmptyHabitats() {
        List<String> habitatList = zooHabitatRepository.findAllHabitat();
        List<String> animalHabitat = zooRepository.findAllHabitat();

         return habitatList.stream()
                .filter(habitat -> !animalHabitat.contains(habitat))
                 .collect(Collectors.toList());
    }
}
