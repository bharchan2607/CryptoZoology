package com.zoo.animals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZooServiceTest {

    @Mock
    ZooRepository repository;
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
}
