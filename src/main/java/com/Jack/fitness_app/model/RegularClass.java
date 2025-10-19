package com.Jack.fitness_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RegularClass {
    @Id
    private String id;
    private String name;
    private String shortDescription;
    private String description;
    private Integer maxNumber;
    private Integer currentNumber;
    private String dateAndTime;
    private Float price;

    public RegularClass() {
    }

    public RegularClass(String id, String name, String shortDescription, String description, Integer maxNumber, Integer currentNumber, String dateAndTime, Float price) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.maxNumber = maxNumber;
        this.currentNumber = currentNumber;
        this.dateAndTime = dateAndTime;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}