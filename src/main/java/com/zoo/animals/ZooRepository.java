package com.zoo.animals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZooRepository extends JpaRepository<AnimalEntity, Long> {

    @Query("from AnimalEntity where animalInfo.mood=?1 and animalInfo.type=?2")
    public List<AnimalEntity> findByMoodAndType(boolean mood, String type);

    @Query("select distinct animalInfo.habitat from AnimalEntity")
    public List<String> findAllHabitat();
}
