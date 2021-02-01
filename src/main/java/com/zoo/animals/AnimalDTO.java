package com.zoo.animals;

import java.util.Objects;

public class AnimalDTO {

    private String name;
    private String type;
    public AnimalDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDTO animalDTO = (AnimalDTO) o;
        return Objects.equals(name, animalDTO.name) && Objects.equals(type, animalDTO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "AnimalDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
