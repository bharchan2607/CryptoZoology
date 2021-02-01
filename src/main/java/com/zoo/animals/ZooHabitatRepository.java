package com.zoo.animals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ZooHabitatRepository extends JpaRepository<AnimalHabitatEntity, Long> {

    public AnimalHabitatEntity findByType(String type);

}
