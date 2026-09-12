package com.whatcanicook.backend.service;


import com.whatcanicook.backend.dto.RecipeResponse;
import com.whatcanicook.backend.model.Recipe;
import com.whatcanicook.backend.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }
    public List<RecipeResponse> searchRecipes(List<String> ingredientNames) {

    List<Recipe> recipes =
            recipeRepository.findByIngredients_NameIn(ingredientNames);

    List<RecipeResponse> responses = new java.util.ArrayList<>();

    for (Recipe recipe : recipes) {

        int matchingIngredients = 0;

        for (var ingredient : recipe.getIngredients()) {

            for (String searchedIngredient : ingredientNames) {

                if (ingredient.getName().equalsIgnoreCase(searchedIngredient.trim())) {
                    matchingIngredients++;
                    break;
                }
            }
        }

        int totalSearchedIngredients = ingredientNames.size();

        int matchPercentage = 0;

        if (totalSearchedIngredients > 0) {
            matchPercentage =
            (matchingIngredients * 100) / totalSearchedIngredients;
        }

        responses.add(new RecipeResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getInstructions(),
                recipe.getCookingTime(),
                recipe.getImageUrl(),
                matchPercentage
        ));
    }

    responses.sort((a, b) ->
            Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()));

    return responses;
}
}
