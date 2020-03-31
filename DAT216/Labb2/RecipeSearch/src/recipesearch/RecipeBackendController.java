package recipesearch;

import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeBackendController {

    private String cuisine;
    private String mainIngredient;
    private String difficulty;
    private int maxPrice;
    private int maxTime;


    private List<String> cuisineList = new ArrayList<>(Arrays.asList("Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike"));
    private List<String> ingredientList = new ArrayList<>(Arrays.asList("Kött", "Fisk", "Kyckling", "Vegetarisk"));
    private List<String> difficultyList = new ArrayList<>(Arrays.asList("Lätt", "Mellan", "Svår"));
    private List<Integer> timeList = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150));


    public List<Recipe> getRecipes() {
        return RecipeDatabase.getSharedInstance().search(new SearchFilter(this.difficulty, this.maxTime, this.cuisine, this.maxPrice, this.mainIngredient));
    }

    public void setCuisine(String cuisine) {
        if (cuisineList.contains(cuisine)) {
            this.cuisine = cuisine;
        } else {
            this.cuisine = null;
        }
    }

    public void setMainIngredient(String mainIngredient) {
        if (ingredientList.contains(mainIngredient)) {
            this.mainIngredient = mainIngredient;
        } else {
            this.mainIngredient = null;
        }
    }

    public void setDifficulty(String difficulty) {
        if (difficultyList.contains(difficulty)) {
            this.difficulty = difficulty;
        } else {
            this.difficulty = null;
        }
    }

    public void setMaxPrice(int maxPrice) {
        if (maxPrice <= 0) {
            this.maxPrice = maxPrice;
        } else {
            this.maxPrice = 0;
        }
    }

    public void setMaxTime(int maxTime) {
        if (timeList.contains(maxTime)) {
            this.maxTime = maxTime;
        } else {
            this.maxTime = 0;
        }
    }


}
