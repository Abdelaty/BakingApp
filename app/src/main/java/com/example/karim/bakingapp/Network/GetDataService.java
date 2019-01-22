package com.example.karim.bakingapp.Network;

import com.example.karim.bakingapp.Models.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("baking.json")
    Call<List<Model>> getData();
}
