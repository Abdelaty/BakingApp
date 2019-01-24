package com.example.karim.bakingapp.Adapters;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karim.bakingapp.Activites.MainActivity;
import com.example.karim.bakingapp.Activites.R;
import com.example.karim.bakingapp.Activites.StepsListActivity;
import com.example.karim.bakingapp.Models.Ingredient;
import com.example.karim.bakingapp.Models.Model;
import com.example.karim.bakingapp.Models.Step;
import com.example.karim.bakingapp.Ui.NewAppWidget;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.MyViewHolder> {
    int position;
    private OnItemClickListener mListener;
    private ArrayList<Model> recipeList;
    private ArrayList<Step> stepsList;
    private ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();
    private Context context;
    private MainActivity mainActivity;

    public RecipeListAdapter(MainActivity mainActivity, ArrayList<Model> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    public RecipeListAdapter(ArrayList<Model> recipeList, OnItemClickListener listener, Context context) {
        this.recipeList = recipeList;
        this.mListener = listener;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_rv_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.foodName.setText(recipeList.get(position).getName());
        holder.people.setText(recipeList.get(position).getServings().toString());
        holder.imageView.setImageResource(R.drawable.cake);
        stepsList = (ArrayList<Step>) recipeList.get(position).getSteps();
        ingredientsList = (ArrayList<Ingredient>) recipeList.get(position).getIngredients();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent myIntent = new Intent(context, StepsListActivity.class);
                myIntent.putParcelableArrayListExtra("steps", stepsList); //Optional parameters
                myIntent.putParcelableArrayListExtra("ingredients", ingredientsList); //Optional parameters

                Intent intent = new Intent(NewAppWidget.ACTION_TEXT_CHANGED);
                intent.putExtra("NewString", ingredientsList.get(position).getQuantity() + " " + ingredientsList.get(position).getMeasure() + ingredientsList.get(position).getIngredient() + "\n");
                context.sendBroadcast(intent);
//                Log.v("stepList", ingredientsList.get(2).getMeasure());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(myIntent);
                //   context.startActivity(ingList);
                widget();
            }
        });
    }

    @Override
    public int getItemCount() {

        Log.v("getItemCount", "size is: " + recipeList.size());

        return recipeList.size();
    }

    void widget() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ingredientsList.size(); i++) {
            int serial = i + 1;
            builder.append(serial + "- " + ingredientsList.get(i).getQuantity() + " " + ingredientsList.get(i).getMeasure() + " of " + ingredientsList.get(i).getIngredient() + "\n");
        }

        SharedPreferences preferences = context.getSharedPreferences("Recipe", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ingredientsWidget", builder.toString());
        editor.apply();


        Log.v("RecipeActivity", preferences.getString("ingredientsWidget", "no data"));

//            Intent intentWidget = new Intent(getBaseContext(),RecipesWidget.class);
//            intentWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, NewAppWidget.class));
//            intentWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
//            sendBroadcast(intentWidget);
        NewAppWidget myWidget = new NewAppWidget();
        myWidget.onUpdate(context, AppWidgetManager.getInstance(context), ids);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, people;

        ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            foodName = view.findViewById(R.id.food_name);
            people = view.findViewById(R.id.people);
            imageView = view.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                    //  Log.v("kll4", "kdgj" + foodList.get(g));
                }
            });
        }
    }
}