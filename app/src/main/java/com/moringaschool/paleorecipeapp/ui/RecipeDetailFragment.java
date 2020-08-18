package com.moringaschool.paleorecipeapp.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.moringaschool.paleorecipeapp.R;
import com.moringaschool.paleorecipeapp.models.Business;
import com.moringaschool.paleorecipeapp.models.Category;
import com.moringaschool.paleorecipeapp.models.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recipeImageView) ImageView mImageLabel;
    @BindView(R.id.recipeNameTextView) TextView mNameLabel;
    @BindView(R.id.ingredientTextView) TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveRecipeButton) TextView mSaveRecipeButton;

    private Business mRecipe;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance(Business recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", Parcels.wrap(recipe));
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipe = Parcels.unwrap(getArguments().getParcelable("recipe"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.get().load(mRecipe.getImageUrl()).into(mImageLabel);

        List<String> categories = new ArrayList<>();

        for (Category category: mRecipe.getCategories()) {
            categories.add(category.getTitle());
        }

        mNameLabel.setText(mRecipe.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", categories));
        mRatingLabel.setText(Double.toString(mRecipe.getRating()) + "/5");
        mPhoneLabel.setText(mRecipe.getPhone());
        mAddressLabel.setText(mRecipe.getLocation().toString());

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        mSaveRecipeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Uri web = Uri.parse(mRecipe.getUrl());
            Intent webIntent = new Intent(Intent.ACTION_VIEW, web);
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Uri number = Uri.parse("tel:" + mRecipe.getPhone());
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel) {
            Uri address = Uri.parse("geo:" + mRecipe.getCoordinates().getLatitude()
                    + "," + mRecipe.getCoordinates().getLongitude()
                    + "?q=(" + mRecipe.getName() + ")");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(mapIntent);
        }

        if(v == mSaveRecipeButton){
            DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SEARCHED_MEAL);
            recipeRef.push().setValue(mRecipe);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}

