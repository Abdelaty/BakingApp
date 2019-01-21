package com.example.karim.bakingapp.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.karim.bakingapp.Activites.R;
import com.example.karim.bakingapp.Models.Step;

import java.util.ArrayList;

public class DetailedStepFragment extends Fragment {
    String stepDescription, videoURL, shortDescription;
    int position = 1;
    TextView fullDescription_tv, shortDescription_tv;
    Button nextButton, backButton;
    private VideoView videoView;
    ArrayList<Step> stepList = new ArrayList<Step>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailed_step_fragment,
                container, false);
        fullDescription_tv = view.findViewById(R.id.step_description);
        shortDescription_tv = view.findViewById(R.id.short_description);
        nextButton = view.findViewById(R.id.next_button);
        backButton = view.findViewById(R.id.back_button);
        videoView = view.findViewById(R.id.step_video);

        Intent intent = getActivity().getIntent();

        stepList = getActivity().getIntent().getParcelableArrayListExtra("steps");
        position = intent.getIntExtra("position", 0);
        setArgument(position);
        setupScreen();
        Log.v(getClass().getName(), "position is:" + position);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "next", Toast.LENGTH_LONG).show();
                if (position < stepList.size()-1) {

                    position++;
                }
                setArgument(position);
                setupScreen();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "back", Toast.LENGTH_LONG).show();
                if (position >0) {
                    position--;
                }
                setArgument(position);
                setupScreen();
            }
        });

        return view;
    }

    public void start(View view) {
        videoView.start();
    }

    public void setArgument(int position) {

        stepDescription = stepList.get(position).getDescription();
        videoURL = stepList.get(position).getVideoURL();
        shortDescription = stepList.get(position).getShortDescription();


        //Setting Video
        Uri videoUri =
                Uri.parse(videoURL);
        videoView.setVideoURI(videoUri);
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.start();
    }

    public void setupScreen() {
        fullDescription_tv.setText(stepDescription);
        shortDescription_tv.setText(shortDescription);
    }


}
