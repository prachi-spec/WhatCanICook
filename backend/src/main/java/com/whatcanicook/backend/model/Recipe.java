package com.whatcanicook.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String instructions;
    private int cookingTime;
    private String imageUrl;
    private int matchPercentage; 

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "recipe_ingredients",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String name, String description, String instructions,
                  int cookingTime, String imageUrl) {
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }
    
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
