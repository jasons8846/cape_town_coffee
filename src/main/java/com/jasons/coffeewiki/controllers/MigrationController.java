package com.jasons.coffeewiki.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrationController {


    @PostMapping("/migratation/company")
    ResponseEntity<String> migrateCompany(){

        return ResponseEntity.status(HttpStatus.OK)
                .body("Company Data Migrated to DynamoDB");
    }

}
