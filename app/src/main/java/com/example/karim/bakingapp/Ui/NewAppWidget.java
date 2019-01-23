package com.example.karim.bakingapp.Ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.karim.bakingapp.Activites.R;
import com.example.karim.bakingapp.Models.Ingredient;

import java.util.ArrayList;

//import com.example.karim.bakingapp.SharedPrefHelper;

/**
 * Implementation of App Widget functionality.
 */

public class NewAppWidget extends AppWidgetProvider {
    public static final String ACTION_TEXT_CHANGED = "android.appwidget.action.APPWIDGET_UPDATE";
    private static ArrayList<Ingredient> mIngredients;
    String ingredient = "Hello";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        SharedPreferences preferences = context.getSharedPreferences("Recipe", 0);
//        StringBuilder Ingredient = new StringBuilder();
        ingredient = preferences.getString("ingredientsWidget", "no data");

        views.setTextViewText(R.id.widget_text, ingredient);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them


        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i("rcivd intnt", "action " + intent.getAction());
        if (intent.getAction().equals(ACTION_TEXT_CHANGED)) {
            // handle intent here
            ingredient = intent.getStringExtra("NewString");
        }
    }
}

