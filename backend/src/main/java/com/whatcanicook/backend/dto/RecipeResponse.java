package com.whatcanicook.backend.dto;



public class RecipeResponse {

    private Long id;
    private String name;
    private String description;
    private String instructions;
    private int cookingTime;
    private String imageUrl;
    private int matchPercentage;

    public RecipeResponse() {
    }

    public RecipeResponse(
            Long id,
            String name,
            String description,
            String instructions,
            int cookingTime,
            String imageUrl,
            int matchPercentage) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.imageUrl = imageUrl;
        this.matchPercentage = matchPercentage;
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
}
