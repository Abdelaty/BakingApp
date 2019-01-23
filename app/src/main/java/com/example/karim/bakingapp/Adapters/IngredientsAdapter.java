package com.example.karim.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karim.bakingapp.Activites.R;
import com.example.karim.bakingapp.Models.Ingredient;
import com.example.karim.bakingapp.Ui.NewAppWidget;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {
    private IngredientsAdapter.OnItemClickListener mListener;
    private ArrayList<Ingredient> ingredientList;
    private Context context;

    public IngredientsAdapter(ArrayList<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    public IngredientsAdapter(ArrayList<Ingredient> ingredientList, IngredientsAdapter.OnItemClickListener listener, Context context) {
        this.ingredientList = ingredientList;
        this.mListener = listener;
        this.context = context;
    }

    public void setOnItemClickListener(IngredientsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public IngredientsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ingredients_rv_item, parent, false);

        return new IngredientsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.MyViewHolder holder, final int position) {
        holder.ingredients_tv.setText(ingredientList.get(position).getQuantity().toString() + "  " + ingredientList.get(position).getMeasure() + "  " + ingredientList.get(position).getIngredient());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, NewAppWidget.class);
                myIntent.putParcelableArrayListExtra("ingredients", ingredientList); //Optional parameters


            }
        });
    }

    @Override
    public int getItemCount() {

//        Log.v("getItemCount", "size is: " + ingredientList.size());

        return ingredientList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ingredients_tv;

        MyViewHolder(View view) {
            super(view);
            ingredients_tv = view.findViewById(R.id.ingredients);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}