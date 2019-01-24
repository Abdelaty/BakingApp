package com.example.karim.bakingapp.Activites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karim.bakingapp.Adapters.RecipeListAdapter;
import com.example.karim.bakingapp.Models.Model;
import com.example.karim.bakingapp.Network.GetDataService;
import com.example.karim.bakingapp.Network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecipeListAdapter recipeListAdapter;
    private RecyclerView recipeList_rv;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeList_rv = findViewById(R.id.recipe_rv);
        textView = findViewById(R.id.textView);
        generateNetworkCall();
    }

    void generateNetworkCall() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Model>> call = service.getData();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Model>> call, @NonNull Response<List<Model>> response) {
                textView.setVisibility(View.GONE);
                generateDataList((ArrayList<Model>) response.body());
                Toast.makeText(MainActivity.this, "Downloading Data Done", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<Model>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please connect to internet!", Toast.LENGTH_SHORT).show();
                textView.setText("Something went wrong...Please connect to internet!");
                textView.setVisibility(View.VISIBLE);
                t.getMessage();
            }

        });

    }

    private void generateDataList(ArrayList<Model> recipesList) {
        recipeListAdapter = new RecipeListAdapter(this, recipesList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recipeList_rv.setLayoutManager(layoutManager);
        recipeList_rv.setAdapter(recipeListAdapter);

    }
}
