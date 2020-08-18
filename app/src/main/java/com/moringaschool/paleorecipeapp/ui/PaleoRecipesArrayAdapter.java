package com.moringaschool.paleorecipeapp.ui;

import android.content.Context;
import android.widget.ArrayAdapter;

public class PaleoRecipesArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mRecipes;
    private String[] mIngredients;

    public PaleoRecipesArrayAdapter(Context mContext, int resource, String[] mRecipes, String[] mIngredients) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mRecipes = mRecipes;
        this.mIngredients = mIngredients;
    }

    @Override
    public Object getItem(int position){
        String recipe = mRecipes[position];
        String ingredient = mIngredients[position];
        return String.format("%s \nServes great: %s", recipe, ingredient);
    }

    @Override
    public int getCount(){
        return mRecipes.length;
    }
}
