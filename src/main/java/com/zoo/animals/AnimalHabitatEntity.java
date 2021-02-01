package com.zoo.animals;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AnimalHabitatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;
    private String habitat;

    public AnimalHabitatEntity() {
    }

    public AnimalHabitatEntity(String type, String habitat) {
        this.type = type;
        this.habitat = habitat;
    }

    public String getType() {
        return type;
    }

    public String getHabitat() {
        return habitat;
    }
}
