package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {
    Optional<Consumer> findByUsername(String username);
}
