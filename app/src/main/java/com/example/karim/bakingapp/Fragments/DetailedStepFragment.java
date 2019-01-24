package com.example.karim.bakingapp.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karim.bakingapp.Activites.R;
import com.example.karim.bakingapp.Models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class DetailedStepFragment extends Fragment {
    private static final String SAVED_PLAYER_POSITION = "player";
    private static final String SAVED_PLAYER_READY = "ready";
    String stepDescription, videoURL, shortDescription;
    int position = 1;
    TextView fullDescription_tv, shortDescription_tv;
    Button nextButton, backButton;
    ArrayList<Step> stepList = new ArrayList<Step>();
    RelativeLayout.LayoutParams params;
    private PlayerView playerView;
    private SimpleExoPlayer mExoPlayer;
    private long pausePosition;
    private boolean mGetWhenReady;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailed_step_fragment,
                container, false);
        fullDescription_tv = view.findViewById(R.id.step_description);
        shortDescription_tv = view.findViewById(R.id.short_description);
        nextButton = view.findViewById(R.id.next_button);
        backButton = view.findViewById(R.id.back_button);
        playerView = (PlayerView) view.findViewById(R.id.video_view);

        Intent intent = getActivity().getIntent();

        stepList = getActivity().getIntent().getParcelableArrayListExtra("steps");
        position = intent.getIntExtra("position", 0);
        setArgument(position);
        setupScreen();
        Log.v(getClass().getName(), "position is:" + position);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < stepList.size() - 1) {

                    position++;
                }
                setArgument(position);
                setupScreen();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    position--;
                }
                setArgument(position);
                setupScreen();
            }
        });

        return view;
    }


    public void setArgument(int position) {

        stepDescription = stepList.get(position).getDescription();
        videoURL = stepList.get(position).getVideoURL();
        shortDescription = stepList.get(position).getShortDescription();


        //Setting Video
        if (videoURL != null && !videoURL.equals("")) {
            playerView.requestFocus();
            playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(videoURL));
            //       releasePlayer();
            checkOrientation();
            getActivity().setTitle(shortDescription);
        } else {
            playerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No Video", Toast.LENGTH_SHORT).show();
        }
    }

    public void setupScreen() {
        fullDescription_tv.setText(stepDescription);
        shortDescription_tv.setText(shortDescription);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {

            pausePosition = mExoPlayer.getCurrentPosition();
            mGetWhenReady = mExoPlayer.getPlayWhenReady();
            outState.putLong(SAVED_PLAYER_POSITION, mExoPlayer.getCurrentPosition());
            outState.putBoolean(SAVED_PLAYER_READY, mExoPlayer.getPlayWhenReady());
        } catch (Exception e) {
            Log.e(TAG, "exoplayer error" + e.toString());
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            pausePosition = savedInstanceState.getLong(SAVED_PLAYER_POSITION);
            mGetWhenReady = savedInstanceState.getBoolean(SAVED_PLAYER_READY);
            if (pausePosition != -1)
                mExoPlayer.seekTo(pausePosition);
            else
                mExoPlayer.seekTo(0);

            mExoPlayer.setPlayWhenReady(mGetWhenReady);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null)
            pausePosition = mExoPlayer.getCurrentPosition();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer != null) {
            releasePlayer();
        }
        if (mExoPlayer == null) {
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
                String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
                playerView.setPlayer(mExoPlayer);
                if (pausePosition != -1)
                    mExoPlayer.seekTo(pausePosition);
                else
                    mExoPlayer.seekTo(0);

                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(mGetWhenReady);

            } catch (Exception e) {
            }
        }
    }

    private void checkOrientation() {
        if (getActivity().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            params = (RelativeLayout.LayoutParams)
                    playerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            playerView.setLayoutParams(params);
        }

    }

}
