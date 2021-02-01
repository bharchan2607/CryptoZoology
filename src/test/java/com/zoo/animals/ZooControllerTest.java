package com.zoo.animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
