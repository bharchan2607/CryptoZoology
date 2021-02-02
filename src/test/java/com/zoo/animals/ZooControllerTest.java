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
    @Autowired
    ZooHabitatRepository habitatRepo;

    ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        zooRepository.deleteAll();
        habitatRepo.deleteAll();
        AnimalHabitatEntity habitat = new AnimalHabitatEntity("flying", "nest");
        AnimalHabitatEntity habitat2 = new AnimalHabitatEntity("swimming", "ocean");
        AnimalHabitatEntity habitat3 = new AnimalHabitatEntity("walking", "forest");

        habitatRepo.save(habitat);
        habitatRepo.save(habitat2);
        habitatRepo.save(habitat3);


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
        System.out.println(animalJson);
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

    @Test
    public void placeAnimalInHabitat_compatibleHabitat() throws Exception {
        AnimalDTO animalDTO = new AnimalDTO("Fish","swimming");
        animalDTO.setTreat(true);
        animalDTO.setHabitat("ocean");
        String animalDTOJson = mapper.writeValueAsString(animalDTO);

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
        Long fishAnimalId = zooRepository.findAll().get(0).getId();
        //Verify
        mockMvc.perform(post("/api/zoo/animals/place/"+fishAnimalId)
                .content(mapper.writeValueAsString(animalDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(animalDTOJson));

    }

    @Test
    public void placeAnimalInHabitat_incompatibleHabitat() throws Exception {

        AnimalDTO animalDTO1 = new AnimalDTO("Bird","flying");
        animalDTO1.setTreat(false);
        animalDTO1.setHabitat("ocean");
        String animalDTO1Json = mapper.writeValueAsString(animalDTO1);


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
        Long birdAnimalId = zooRepository.findAll().get(1).getId();

        //feed animal to make it happy
        mockMvc.perform(get("/api/zoo/animals/feed/"+birdAnimalId))
                .andExpect(status().isOk());

        //Verify
        mockMvc.perform(post("/api/zoo/animals/place/"+birdAnimalId)
                .content(mapper.writeValueAsString(animalDTO1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(animalDTO1Json));

    }

    @Test
    public void searchForAnimalsByMoodAndType() throws Exception {

        List<AnimalDTO> animalList = List.of(new AnimalDTO("Bird","flying", true, "nest")
        ,new AnimalDTO("Owl","flying", true, "nest"));
        String animalListJson = mapper.writeValueAsString(animalList);
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Fish","swimming", true, "ocean")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Bird","flying", true, "nest")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Owl","flying", true, "nest")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        boolean mood = true;
        String type = "flying";

        mockMvc.perform(get("/api/zoo/animals/search/"+mood+"/"+type))
                .andExpect((status().isOk()))
                .andExpect(content().string(animalListJson));

    }

    @Test
    public void searchForEmptyHabitats() throws Exception {
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Fish","swimming", true, "ocean")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Bird","flying", true, "nest")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        mockMvc.perform(post("/api/zoo/animals")
                .content(mapper.writeValueAsString(new AnimalDTO("Owl","flying", true, "nest")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(get("/api/zoo/animals/search/emptyHabitats"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(List.of("forest"))));

    }

}
