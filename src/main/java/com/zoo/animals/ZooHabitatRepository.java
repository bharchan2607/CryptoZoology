package com.zoo.animals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZooHabitatRepository extends JpaRepository<AnimalHabitatEntity, Long> {

    public AnimalHabitatEntity findByType(String type);

    @Query("select habitat from AnimalHabitatEntity")
    public List<String> findAllHabitat();

}
