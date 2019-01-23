package com.example.karim.bakingapp.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.karim.bakingapp.Fragments.StepsListFragment;
import com.example.karim.bakingapp.Models.Ingredient;
import com.example.karim.bakingapp.Models.Step;

import java.util.ArrayList;

public class StepsListActivity extends AppCompatActivity {
    /// ArrayList<Ingredient> ingredientsArrList = new ArrayList<Ingredient>();
    ArrayList<Ingredient> ingredientsList;
    private RecyclerView stepsList_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_list);
        stepsList_rv = findViewById(R.id.steps_rv);


        ArrayList<Step> stepsList = getIntent().getParcelableArrayListExtra("steps");
        // ArrayList<Ingredient> ingredientsList = getIntent().getParcelableArrayListExtra("ingredients");
        ingredientsList = getIntent().getParcelableArrayListExtra("ingredients");
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.steps_list_fragment, new StepsListFragment());
        ft.commit();

        Intent myIntent = new Intent(getApplicationContext(), StepsListFragment.class);
        myIntent.putParcelableArrayListExtra("steps", stepsList); //Optional parameters
        myIntent.putParcelableArrayListExtra("ingredients", ingredientsList); //Optional parameters
//        setPreference();

    }
//
}
