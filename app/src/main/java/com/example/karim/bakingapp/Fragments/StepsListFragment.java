package com.example.karim.bakingapp.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karim.bakingapp.Activites.R;
import com.example.karim.bakingapp.Adapters.IngredientsAdapter;
import com.example.karim.bakingapp.Adapters.StepsAdapter;
import com.example.karim.bakingapp.Models.Ingredient;
import com.example.karim.bakingapp.Models.Step;

import java.util.ArrayList;

public class StepsListFragment extends Fragment {
    StepsAdapter stepsListAdapter;
    IngredientsAdapter ingredientsAdapter;

    private RecyclerView stepsList_rv, ingredientsList_rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_list_fragment,
                container, false);
        stepsList_rv = view.findViewById(R.id.steps_rv);
        ingredientsList_rv = view.findViewById(R.id.ingredients_rv);


        ArrayList<Step> stepsList = getActivity().getIntent().getParcelableArrayListExtra("steps");
        ArrayList<Ingredient> ingredientList = getActivity().getIntent().getParcelableArrayListExtra("ingredients");

        Intent myIntent = new Intent(getActivity(), DetailedStepFragment.class);
        myIntent.putParcelableArrayListExtra("steps", stepsList); //Optional parameters


        //  Log.v("steps activity test", stepsList.get(2).getDescription());
        generateDataList(stepsList);
        generateIngredientsList(ingredientList);
        return view;
    }

    private void generateDataList(ArrayList<Step> stepsList) {
        stepsListAdapter = new StepsAdapter(stepsList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        stepsList_rv.setLayoutManager(layoutManager);
        stepsList_rv.setAdapter(stepsListAdapter);
        stepsListAdapter.notifyDataSetChanged();


    }

    private void generateIngredientsList(ArrayList<Ingredient> ingredientsList) {
        ingredientsAdapter = new IngredientsAdapter(ingredientsList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ingredientsList_rv.setLayoutManager(layoutManager);
        ingredientsList_rv.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();

    }
}
