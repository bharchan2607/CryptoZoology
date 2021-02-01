package com.zoo.animals;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private boolean mood;

    @OneToOne
    private AnimalHabitatEntity animalHabitat;

    public AnimalEntity() {
    }

    public AnimalEntity(String name, String type) {
        this.name = name;
        this.type = type;
        this.mood = false;

    }

    public AnimalEntity(String name, String type, boolean mood) {
        this.name = name;
        this.type = type;
        this.mood = mood;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean getMood() {
        return mood;
    }

    public void setMood(boolean mood) {
        this.mood = mood;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalEntity that = (AnimalEntity) o;
        return mood == that.mood && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, mood);
    }

    @Override
    public String toString() {
        return "AnimalEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", mood=" + mood +
                '}';
    }
}
