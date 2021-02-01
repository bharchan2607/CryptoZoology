package com.zoo.animals;

import java.util.Objects;

public class AnimalDTO {

    private String name;
    private String type;
    private boolean treat;
    private String habitat;

    public AnimalDTO(String name, String type) {
        this.name = name;
        this.type = type;
        this.treat = false;
    }

    public AnimalDTO(String name, String type, boolean treat) {
        this.name = name;
        this.type = type;
        this.treat = treat;
    }

    public AnimalDTO() {
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean getTreat() {
        return treat;
    }

    public void setTreat(boolean treat) {
        this.treat = treat;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDTO animalDTO = (AnimalDTO) o;
        return treat == animalDTO.treat && Objects.equals(name, animalDTO.name) && Objects.equals(type, animalDTO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, treat);
    }

    @Override
    public String toString() {
        return "AnimalDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", treat=" + treat +
                '}';
    }
}
