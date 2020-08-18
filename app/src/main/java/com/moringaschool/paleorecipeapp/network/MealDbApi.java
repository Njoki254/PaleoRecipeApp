package com.moringaschool.paleorecipeapp.network;

import com.moringaschool.paleorecipeapp.models.YelpBusinessesSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealDbApi {

    @GET("businesses/search")
    Call<YelpBusinessesSearchResponse> getRecipes(
            @Query("location") String location,
            @Query("term") String term
    );
}
