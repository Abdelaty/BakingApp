package com.example.karim.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karim.bakingapp.Activites.DetailsActivity;
import com.example.karim.bakingapp.Activites.R;
import com.example.karim.bakingapp.Activites.StepsListActivity;
import com.example.karim.bakingapp.Models.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {
    StepsListActivity stepsListActivity;
    private StepsAdapter.OnItemClickListener mListener;
    private ArrayList<Step> stepsList = new ArrayList<Step>();
    private Context context;

    public StepsAdapter(ArrayList<Step> stepsList, Context context) {
        this.stepsList = stepsList;
        this.context = context;
    }

//    public StepsAdapter(ArrayList<Step> stepsList, StepsAdapter.OnItemClickListener listener, Context context) {
//        this.stepsList = stepsList;
//        this.mListener = listener;
//        this.context = context;
//    }

    public void setOnItemClickListener(StepsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public StepsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.step_rv_item, parent, false);

        return new StepsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.MyViewHolder holder, final int position) {
        holder.step_name.setText(stepsList.get(position).getId().toString() + "- " + stepsList.get(position).getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(getClass().getName(), "item Clicked:" + stepsList.get(position));
                Toast.makeText(context, "Item num" + stepsList.get(position) + " Clicked", Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(context, DetailsActivity.class);
                myIntent.putExtra("position", position); //Optional parameters ing
                myIntent.putParcelableArrayListExtra("steps", stepsList); //Optional parameters
                Log.v(getClass().getName(),"position is:"+position);

                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {

        Log.v("getItemCount", "size is: " + stepsList.size());

        return stepsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView step_name;
        Button ing_Button;

        MyViewHolder(View view) {
            super(view);
            step_name = view.findViewById(R.id.step_tv);

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