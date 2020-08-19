package com.moringaschool.paleorecipeapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.paleorecipeapp.R;
import com.moringaschool.paleorecipeapp.models.Business;
import com.moringaschool.paleorecipeapp.models.Constants;
import com.moringaschool.paleorecipeapp.ui.RecipeDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;

    public FirebaseRecipeViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRecipe(Business recipe) {
        ImageView recipeImageView = (ImageView) mView.findViewById(R.id.recipeImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.recipeNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        Picasso.get().load(recipe.getImageUrl()).into(recipeImageView);

        nameTextView.setText(recipe.getName());
        categoryTextView.setText(recipe.getCategories().get(0).getTitle());
        ratingTextView.setText("Rating: " + recipe.getRating() + "/5");
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Business> recipes = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    recipes.add(snapshot.getValue(Business.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("recipes", Parcels.wrap(recipes));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
//we add static variables to hold the width and height of our images for Picasso.
//
//We then add member variables to hold the view and context which we set in our constructor.
//
//We also implement the View.OnClickListener interface and set the click listener on our itemView.
//
//In our bindRestaurant() method, we first bind the views and then set the image and text views.
//
//Finally, in the onClick() method, we create a singleValueEventListener to grab out the current list of restaurants from Firebase which we pass along to the RestaurantDetailActivity in the form of an intent extra. We will need this ArrayList when constructing an instance of the RestaurantDetailFragment.