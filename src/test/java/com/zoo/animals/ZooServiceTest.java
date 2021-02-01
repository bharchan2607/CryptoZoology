package com.zoo.animals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZooServiceTest {

    @Mock
    ZooRepository repository;
    @Mock
    ZooHabitatRepository habitatRepository;
    @InjectMocks
    ZooService service;

    @Test
    public void addAnimal(){
        AnimalEntity animal = new AnimalEntity("fish", "swimming");
        AnimalDTO animalDTO = new AnimalDTO("fish", "swimming");
        when(repository.save(animal)).thenReturn(animal);
        AnimalDTO actualDTO = service.addAnimal(new AnimalDTO("fish","swimming"));
        verify(repository,times(1)).save(animal);
        assertEquals(animalDTO,actualDTO);

    }

    @Test
    public void getAllAnimals(){
        List<AnimalEntity> animalList = List.of(new AnimalEntity("fish", "swimming")
                ,new AnimalEntity("Bird", "flying"));
        when(repository.findAll()).thenReturn(animalList);
        List<AnimalDTO> expectedDTO = List.of(new AnimalDTO("fish", "swimming")
                ,new AnimalDTO("Bird", "flying"));
        List<AnimalDTO> actualDTO = service.getAllAnimals();
        verify(repository, times(1)).findAll();
        assertEquals(expectedDTO, actualDTO);

    }

    @Test
    public void feedAnimals(){
        AnimalEntity animal = new AnimalEntity("Bird", "flying", true);
        when(repository.save(any())).thenReturn(animal);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(animal));
        service.feedAnimals(1L);
        verify(repository, times(1)).save(animal);
    }
    @Test
    public void placeAnimalsInHabitat(){
        AnimalEntity animal = new AnimalEntity("Bird", "flying", true);
        animal.setHabitat("nest");
        AnimalDTO animalDTO = new AnimalDTO(
                "Bird", "flying", true);
        animalDTO.setHabitat("nest");
        when(repository.save(any())).thenReturn(animal);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(animal));
        when(habitatRepository.findByType("flying")).thenReturn(new AnimalHabitatEntity("flying","nest"));
        service.placeAnimalsInHabitat(1L, animalDTO);
        verify(repository, times(1)).save(animal);

    }
}
