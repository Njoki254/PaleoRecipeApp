package com.moringaschool.paleorecipeapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringaschool.paleorecipeapp.models.Business;
import com.moringaschool.paleorecipeapp.ui.RecipeDetailFragment;

import java.util.List;

public class RecipePagerAdapter extends FragmentPagerAdapter {
    private List<Business> mRecipes;

    public RecipePagerAdapter(FragmentManager fm, int behavior, List<Business> recipes) {
        super(fm, behavior);
        mRecipes = recipes;
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeDetailFragment.newInstance(mRecipes.get(position));
    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRecipes.get(position).getName();
    }
}