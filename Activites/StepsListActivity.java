package com.example.karim.bakingapp.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.karim.bakingapp.Fragments.StepsListFragment;
import com.example.karim.bakingapp.Models.Ingredient;
import com.example.karim.bakingapp.Models.Step;
import com.example.karim.bakingapp.SharedPrefHelper;

import java.util.ArrayList;

public class StepsListActivity extends AppCompatActivity {
    private RecyclerView stepsList_rv;
    /// ArrayList<Ingredient> ingredientsArrList = new ArrayList<Ingredient>();
    ArrayList<Ingredient> ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_list);
        stepsList_rv = findViewById(R.id.steps_rv);
        Context context = getApplicationContext();


        ArrayList<Step> stepsList = getIntent().getParcelableArrayListExtra("steps");
        ArrayList<Ingredient> ingredientsList = getIntent().getParcelableArrayListExtra("ingredients");
        ingredientsList = getIntent().getParcelableArrayListExtra("ingredients");
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.steps_list_fragment, new StepsListFragment());
        ft.commit();

        Intent myIntent = new Intent(getApplicationContext(), StepsListFragment.class);
        myIntent.putParcelableArrayListExtra("steps", stepsList); //Optional parameters
        myIntent.putParcelableArrayListExtra("ingredients", ingredientsList); //Optional parameters
        setPreference();

    }

    private void setPreference() {
        ArrayList<Ingredient> list = SharedPrefHelper.getList(this);
        if (list != null) {
            SharedPrefHelper.ClearArrayList(this);
            SharedPrefHelper.saveArrayList(ingredientsList, this);
        } else {
            SharedPrefHelper.saveArrayList(ingredientsList, this);
            Log.v("FFF", ingredientsList.get(2).getIngredient());

        }
        Log.v("FFF1", ingredientsList.get(2).getIngredient());
    }
}
