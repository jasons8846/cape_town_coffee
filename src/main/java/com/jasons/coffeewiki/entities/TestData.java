package com.jasons.coffeewiki.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestData {

    @Id
    private Integer id;
    private String name;

    public TestData(){};

    public TestData(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
