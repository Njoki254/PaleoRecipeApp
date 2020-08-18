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
import com.moringaschool.paleorecipeapp.models.Business;
import com.moringaschool.paleorecipeapp.models.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRecipeListActivity extends AppCompatActivity {
    private DatabaseReference mRecipeReference;
    private FirebaseRecyclerAdapter<Business, FirebaseRecipeViewHolder> mFirebaseAdapter;

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
        FirebaseRecyclerOptions<Business> options =
                new FirebaseRecyclerOptions.Builder<Business>()
                        .setQuery(mRecipeReference, Business.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Business, FirebaseRecipeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseRecipeViewHolder firebaseRecipeViewHolder, int position, @NonNull Business recipe) {
                firebaseRecipeViewHolder.bindRecipe(recipe);
            }

            @NonNull
            @Override
            public FirebaseRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
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

