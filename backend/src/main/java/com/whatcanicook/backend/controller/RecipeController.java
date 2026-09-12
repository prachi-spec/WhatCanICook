package com.whatcanicook.backend.controller;


import com.whatcanicook.backend.dto.RecipeResponse;
import com.whatcanicook.backend.model.Recipe;
import com.whatcanicook.backend.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }
    @PostMapping("/search")
    public List<RecipeResponse> searchRecipes(@RequestBody List<String> ingredientNames) {
        return recipeService.searchRecipes(ingredientNames);
    }
}