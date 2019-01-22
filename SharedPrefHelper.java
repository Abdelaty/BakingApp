/*package com.example.karim.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.karim.bakingapp.Models.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class SharedPrefHelper {
    private static final String KEY_SHARED = "shared";
    private static final String KEY_SHARED_PERE = "shared_PRE";

    public static void saveArrayList(ArrayList<Ingredient> list, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEY_SHARED_PERE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_SHARED, json);
        editor.apply();
        Log.v("saveArrayList",list.get(2).getIngredient());
    }

    public static void ClearArrayList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEY_SHARED_PERE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public static ArrayList<Ingredient> getList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEY_SHARED_PERE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_SHARED, null);
        Type type = new TypeToken<ArrayList<Ingredient>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
*/