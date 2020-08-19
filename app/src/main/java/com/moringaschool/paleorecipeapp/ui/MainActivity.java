package com.moringaschool.paleorecipeapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.moringaschool.paleorecipeapp.R;
import com.moringaschool.paleorecipeapp.models.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private DatabaseReference mSearchedLocationReference;
    private ValueEventListener mSearchedLocationReferenceListener;

    @BindView(R.id.savedRecipesButton) Button mSavedRecipesButton;

    @BindView(R.id.findRecipesButton) Button mFindRecipesButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedLocationReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_SEARCHED_MEAL);

        mSearchedLocationReferenceListener = mSearchedLocationReference.addValueEventListener(new ValueEventListener() { //attach listener

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //something changed!
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();

                    Log.d("Locations updated", "location: " + location); //log
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //update UI here if error occurred.

            }
        });
        //define a Firebase DatabaseReference object called mSearchedLocationReference and pass it the child key of the searchedLocations node (as covered in Data Persistence: Writing to Firebase lesson). Then, we call addValueEventListener()on our mSearchedLocationReference object to attach a new ValueEventListener (which we provide as a parameter).
        //
        //As mentioned above, ValueEventListeners have two methods that must be overridden; onDataChange() and onCancelled():
        //
        //To recap:
        //
        //onDataChange() is called whenever data at the specified node changes. Such as adding a new zip code. It will return a dataSnapshot object, which is essentially a read-only copy of the Firebase state.
        //
        //onCancelled() is called if the listener is unsuccessful for any reason. We won't add any code here right now, but could in the future.
        //
        //In our onDataChange method we'll snag the values returned in the dataSnapshot, loop through each of the children with the getChildren() method, and print their values to the logcat. Other methods we can call on a dataSnapshot include .child() and .getKey().

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };




//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

        mFindRecipesButton.setOnClickListener(this);
        mSavedRecipesButton.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        if(v == mFindRecipesButton) {
            String location = mLocationEditText.getText().toString();

            saveLocationToFirebase(location);

//            if(!(location).equals("")) {
//                addToSharedPreferences(location);
//            }


            Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);
        }

        if (v == mSavedRecipesButton) {
            Intent intent = new Intent(MainActivity.this, SavedRecipeListActivity.class);
            startActivity(intent);
        }
    }

//call the push() method before setting the value. This will ensure each new entry is added to the node under a unique, randomly generated id called a push id:
    public void saveLocationToFirebase(String location) {
        mSearchedLocationReference.push().setValue(location);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
    }
    //    private void addToSharedPreferences(String location) {
//        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
//    }
}
//we declare the member variable mSearchedLocationReferenceListenerand assign it to the event listener. Then, we override the activity's onDestroy() method that automatically runs when the activity is halted. We explicitly instruct our app to remove the listener from our Firebase node when the activity is destroyed.
//
//Note that the onDestroy() method is an override for the activity, not the listener. It is defined in the top level of the class, not nested within the addValueEventListener() block.