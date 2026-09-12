package com.whatcanicook.backend.repository;



import com.whatcanicook.backend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByIngredients_NameIn(List<String> ingredientNames);
}
