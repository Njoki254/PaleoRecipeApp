package com.moringaschool.paleorecipeapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.paleorecipeapp.R;
import com.moringaschool.paleorecipeapp.adapters.FirebaseRecipeListAdapter;
import com.moringaschool.paleorecipeapp.models.Constants;
import com.moringaschool.paleorecipeapp.models.Recipe;
import com.moringaschool.paleorecipeapp.util.OnStartDragListener;
import com.moringaschool.paleorecipeapp.util.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRecipeListActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mRecipeReference;
    private FirebaseRecipeListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);


        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mRecipeReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES).child(uid);
        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                        .setQuery(mRecipeReference, Recipe.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecipeListAdapter(options, mRecipeReference, this, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
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
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }
}
//We add the ItemTouchHelper as a member variable so that we can use it in the OnStartDragListener's onStartDrag() method.
//
//In the FirebaseRestaurantListAdapter constructor, this refers to the OnStartDragListener and the Context. Both are necessary to construct an instance of a FirebaseRestaurantListAdapter.
//
//The SimpleItemTouchHelperCallback takes an adapter as a parameter so we pass it the instance of the FirebaseRestaurantListAdapter we just created.
//
//The ItemTouchHelper takes a ItemTouchHelper.Callback as an argument so we can pass it the instance of the SimpleItemTouchHelperCallback that we just created using our adapter.
//
//To enable the interfaces to communicate with the necessary callbacks, we must attach the ItemTouchHelper to our RecyclerView using the attachToRecyclerView() method.
//
//Finally, we call the startDrag() method on the instance of our ItemTouchHelper inside of the onStartDrag() override which will eventually send our touch events back to our SimpleItemTouchHelperCallback.