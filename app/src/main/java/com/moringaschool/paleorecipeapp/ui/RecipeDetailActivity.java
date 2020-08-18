package com.moringaschool.paleorecipeapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.moringaschool.paleorecipeapp.R;
import com.moringaschool.paleorecipeapp.adapters.RecipePagerAdapter;
import com.moringaschool.paleorecipeapp.models.Business;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private RecipePagerAdapter adapterViewPager;
    List<Business> mRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);

        mRecipes = Parcels.unwrap(getIntent().getParcelableExtra("recipes"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new RecipePagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mRecipes);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }
}
