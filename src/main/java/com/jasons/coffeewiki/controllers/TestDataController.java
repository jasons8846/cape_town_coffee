package com.jasons.coffeewiki.controllers;

import com.jasons.coffeewiki.entities.TestData;
import com.jasons.coffeewiki.repositories.TestDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDataController {

//    @Value("${app.api.key}")
//    String apiKey;
//
//    @GetMapping("/")
//    public String getTestData(){
//        return apiKey;
//    }
}
