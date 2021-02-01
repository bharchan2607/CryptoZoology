package com.zoo.animals;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ZooRepository extends JpaRepository<AnimalEntity, Long> {
}
