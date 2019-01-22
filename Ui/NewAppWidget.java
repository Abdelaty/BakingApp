package com.example.karim.bakingapp.Ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.karim.bakingapp.Activites.R;
import com.example.karim.bakingapp.Models.Ingredient;
import com.example.karim.bakingapp.SharedPrefHelper;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static ArrayList<Ingredient> mIngredients;
    String ingredient;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        mIngredients = SharedPrefHelper.getList(context);
        //  Log.wtf("SHIT", ingredient);
        Log.wtf("SHIT1", mIngredients.get(2).getIngredient());

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //  ingredient = ingredientList.get(position).getIngredient();
        //        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
//        ingredientList = getActivity().getIntent().getParcelableArrayListExtra("steps");
//        Intent intent = new Intent(context, StepsListActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        views.setOnClickPendingIntent(R.id.imageView2, pendingIntent);
//
        StringBuilder Ingredient = new StringBuilder();

        for (Ingredient ingredients : mIngredients) {
            Ingredient.append(String.valueOf(ingredients.getIngredient())).append("  ")
                    .append(ingredients.getMeasure()).append("  ")
                    .append(String.valueOf(ingredients.getQuantity())).append("\n");
        }
        views.setTextViewText(R.id.widget_text, ingredient);

        // Instruct the widget manager to update the widget
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

}

