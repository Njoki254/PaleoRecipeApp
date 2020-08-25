package com.moringaschool.paleorecipeapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.paleorecipeapp.R;
import com.moringaschool.paleorecipeapp.adapters.FirebaseRecipeViewHolder;
import com.moringaschool.paleorecipeapp.models.Recipe;
import com.moringaschool.paleorecipeapp.models.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.paleorecipeapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRecipeListActivity extends AppCompatActivity {
    private DatabaseReference mRecipeReference;
    private FirebaseRecyclerAdapter<Recipe, FirebaseRecipeViewHolder> mFirebaseAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);


        mRecipeReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                        .setQuery(mRecipeReference, Recipe.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Recipe, FirebaseRecipeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseRecipeViewHolder firebaseRecipeViewHolder, int position, @NonNull Recipe recipe) {
                firebaseRecipeViewHolder.bindRecipe(recipe);
            }

            @NonNull
            @Override
            public FirebaseRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item_drag, parent, false);
                return new FirebaseRecipeViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
}

//initialize our DatabaseReference, FirebaseRecyclerAdapter, and RecyclerView member variables.
//
//We then pass in the activity_restaurants layout into the setContentView() method to display the correct layout.
//
//Next, we set the mRestaurantReference using the "restaurants" child node key from our Constants class.
//
//We then create a method to set up the FirebaseAdapter by first creating a FirebaseRecyclerOptions object which is cast into the model class, we build the object by setting the query (or database reference) (by) passing in the database-reference and the Model class the objects will be parsed into
//
//Inside of the onBindViewHolder() method, we call the bindRestaurant() method on our viewHolder to set the appropriate text and image with the given restaurant.
//
//We then set the adapter on our RecyclerView.
//
//Finally, we need to clean up the FirebaseAdapter. When the activity is stops, we need to call onStop() on the adapter so that it can stop listening for changes in the Firebase database.