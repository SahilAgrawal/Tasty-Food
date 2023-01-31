package com.tastyfoodpvtltd.restaurantsearchservice.domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("restaurant")
public class Restaurant {
    private String id;
    private String name;
    private List<Item> items;
    private String location;
    private List<String> cuisines;
    private Geolocation geolocation;
    private double budgetPerPerson;

    public Restaurant() {

    }

    public Restaurant(String id, String name, List<Item> items, String location, List<String> cuisines,
            Geolocation geolocation, double budgetPerPerson) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.location = location;
        this.cuisines = cuisines;
        this.geolocation = geolocation;
        this.budgetPerPerson = budgetPerPerson;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public double getBudgetPerPerson() {
        return budgetPerPerson;
    }

    public void setBudgetPerPerson(double budgetPerPerson) {
        this.budgetPerPerson = budgetPerPerson;
    }

    @Override
    public String toString() {
        return "Restaurant [id=" + id + ", name=" + name + ", items=" + items + ", location=" + location + ", cuisines="
                + cuisines + ", geolocation=" + geolocation + ", budgetPerPerson=" + budgetPerPerson + "]";
    }

}
