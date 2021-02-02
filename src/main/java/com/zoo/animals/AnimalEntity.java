package com.zoo.animals;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private AnimalInfo animalInfo;

    public AnimalEntity() {
    }

    public AnimalEntity(String name, String type) {
        this.name = name;
        this.animalInfo = new AnimalInfo(type, false);

    }

    public AnimalEntity(String name, String type, boolean mood) {
        this.name = name;
        this.animalInfo = new AnimalInfo(type, mood);
    }

    public AnimalEntity(String name, String type, boolean mood, String habitat) {
        this.name = name;
        this.animalInfo = new AnimalInfo(type, mood, habitat);
    }

    public AnimalInfo getAnimalInfo() {
        return animalInfo;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalEntity that = (AnimalEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(animalInfo, that.animalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, animalInfo);
    }

    @Override
    public String toString() {
        return "AnimalEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", animalInfo=" + animalInfo +
                '}';
    }
}
