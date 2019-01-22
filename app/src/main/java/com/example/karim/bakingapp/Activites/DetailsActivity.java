package com.example.karim.bakingapp.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.karim.bakingapp.Fragments.DetailedStepFragment;
import com.example.karim.bakingapp.Models.Step;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);
        ArrayList<Step> stepsList = new ArrayList<Step>();
        stepsList = getIntent().getParcelableArrayListExtra("steps");


        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);


        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.details_frame, new DetailedStepFragment());
        ft.commit();


        Intent myIntent = new Intent(getApplication(), DetailedStepFragment.class);
        myIntent.putExtra("position", position); //Optional parameters
        Log.v(getLocalClassName(), "position is:" + position);

        myIntent.putParcelableArrayListExtra("steps", stepsList);

    }


}

