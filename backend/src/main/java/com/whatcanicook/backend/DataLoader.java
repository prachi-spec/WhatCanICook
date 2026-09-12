package com.whatcanicook.backend;

import com.whatcanicook.backend.model.Ingredient;
import com.whatcanicook.backend.model.Recipe;
import com.whatcanicook.backend.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(RecipeRepository recipeRepository) {

        return args -> {

            // Don't import the recipes again if they already exist
            if (recipeRepository.count() > 0) {
                System.out.println("Recipes already exist. Skipping CSV import.");
                return;
            }

            // Find recipes.csv inside src/main/resources
            InputStream inputStream =
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream("recipes.csv");

            if (inputStream == null) {
                System.out.println("ERROR: recipes.csv not found!");
                return;
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            inputStream,
                            StandardCharsets.UTF_8
                    )
            );

            String line;
            boolean firstLine = true;

            List<Recipe> recipes = new ArrayList<>();

            while ((line = reader.readLine()) != null) {

                // Skip the CSV header
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // Properly handle commas inside quoted CSV fields
                String[] data = parseCSVLine(line);

                // Make sure the row has all required columns
                if (data.length < 6) {
                    continue;
                }

                String name = data[0].trim();
                String description = data[1].trim();
                String instructions = data[2].trim();

                int cookingTime;

                try {
                    cookingTime = Integer.parseInt(data[3].trim());
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Skipping recipe because cooking time is invalid: "
                                    + name
                    );
                    continue;
                }

                // Category is currently in the CSV but our Recipe model
                // doesn't store it yet.
                String category = data[4].trim();

                String[] ingredientNames = data[5].split("\\|");

                List<Ingredient> ingredients = new ArrayList<>();

                for (String ingredientName : ingredientNames) {

                    String cleanedName = ingredientName.trim();

                    if (!cleanedName.isEmpty()) {
                        ingredients.add(
                                new Ingredient(cleanedName)
                        );
                    }
                }

                Recipe recipe = new Recipe(
                        name,
                        description,
                        instructions,
                        cookingTime,
                        ""
                );

                recipe.setIngredients(ingredients);

                recipes.add(recipe);
            }

            // Save all recipes to MySQL
            recipeRepository.saveAll(recipes);

            System.out.println(
                    recipes.size()
                            + " recipes imported from CSV!"
            );
        };
    }

    // Reads CSV rows correctly, including commas inside "quotes"
    private String[] parseCSVLine(String line) {

        List<String> values = new ArrayList<>();

        StringBuilder current = new StringBuilder();

        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char c = line.charAt(i);

            if (c == '"') {

                insideQuotes = !insideQuotes;

            } else if (c == ',' && !insideQuotes) {

                values.add(current.toString());
                current.setLength(0);

            } else {

                current.append(c);
            }
        }

        // Add the final value
        values.add(current.toString());

        return values.toArray(new String[0]);
    }
}