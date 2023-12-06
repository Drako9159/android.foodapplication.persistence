package com.example.reto5uveg.entity;


public class Food {
    private int _id;
    private String name;
    private Double price;
    private String description;
    private FoodType foodType;

    public Food(int _id, String name, Double price, String description, FoodType foodType) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.foodType = foodType;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
}
