package com.zoo.animals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ZooControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ZooRepository zooRepository;

    ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        zooRepository.deleteAll();
        mapper = new ObjectMapper();
    }

    @Test
    public void addAnimals() throws Exception {
        AnimalDTO animal = new AnimalDTO("Fish","swimming");
        String animalJson = mapper.writeValueAsString(animal);
        mockMvc.perform(post("/api/zoo/animals")
        .content(animalJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(animalJson));
    }

    @Test
    public void viewAnimals() throws Exception {
        List<AnimalDTO> animal = List.of(new AnimalDTO("Fish","swimming")
                ,new AnimalDTO("Bird","flying"));
        String animalJson = mapper.writeValueAsString(animal);

        //Setup Data
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Fish","swimming")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Bird","flying")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        //Verify
        mockMvc.perform(get("/api/zoo/animals"))
                .andExpect(status().isOk())
                .andExpect(content().string(animalJson));
    }

    @Test
    public void feedAnimals() throws Exception {
        List<AnimalDTO> animal = List.of(new AnimalDTO("Fish","swimming")
                ,new AnimalDTO("Bird","flying"));
        String animalJson = mapper.writeValueAsString(animal);

        //Setup Data
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Fish","swimming")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Bird","flying")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        Long animalId = zooRepository.findAll().get(1).getId();
        //Verify
        mockMvc.perform(get("/api/zoo/animals/feed/"+animalId))
                .andExpect(status().isOk());

        //Verify
        mockMvc.perform(get("/api/zoo/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].treat").value(false))
                .andExpect(jsonPath("$.[1].treat").value(true))
                .andReturn();

        mockMvc.perform(get("/api/zoo/animals/feed/"+animalId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/zoo/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].treat").value(false))
                .andExpect(jsonPath("$.[1].treat").value(true))
                .andReturn();



    }

}
