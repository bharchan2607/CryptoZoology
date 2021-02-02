package com.zoo.animals;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AnimalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private boolean mood;
    private String habitat;

    public AnimalInfo() {
    }

    public AnimalInfo(String type) {
        this.type = type;
    }

    public AnimalInfo(boolean mood, String habitat) {
        this.mood = mood;
        this.habitat = habitat;
    }

    public AnimalInfo(String type, boolean mood, String habitat) {
        this.type = type;
        this.mood = mood;
        this.habitat = habitat;
    }

    public AnimalInfo(String type, boolean mood) {
        this.type = type;
        this.mood = mood;
    }

    public String getType() {
        return type;
    }

    public boolean isMood() {
        return mood;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setMood(boolean mood) {
        this.mood = mood;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalInfo that = (AnimalInfo) o;
        return mood == that.mood && Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(habitat, that.habitat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, mood, habitat);
    }

    @Override
    public String toString() {
        return "AnimalInfo{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", mood=" + mood +
                ", habitat='" + habitat + '\'' +
                '}';
    }
}
