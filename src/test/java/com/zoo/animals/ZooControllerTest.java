package com.zoo.animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ZooControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper;

    @BeforeEach
    public void setup(){
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

}
