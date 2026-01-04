package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.TestData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDataRepository extends JpaRepository<TestData, Integer>{

    TestData getTestDataById(Integer id);
}
