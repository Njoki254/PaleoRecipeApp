
package com.moringaschool.paleorecipeapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeSearchResponse {

    @SerializedName("meals")
    @Expose
    private List<Meal> meals = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RecipeSearchResponse() {
    }

    /**
     * 
     * @param meals
     */
    public RecipeSearchResponse(List<Meal> meals) {
        super();
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}
