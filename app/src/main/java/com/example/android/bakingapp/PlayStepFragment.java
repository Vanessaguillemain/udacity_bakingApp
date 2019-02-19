package com.example.android.bakingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.utils.Utils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

/**
 * Created by vanessa on 17/02/2019.
 */

public class PlayStepFragment extends Fragment {

    private Recipe currentRecipe;
    private List<RecipeStep> recipeSteps;
    private int currentStep;
    private int stepsSize;
    private Button mBtnBefore;
    private Button mBtnAfter;
    private TextView mTextViewDescription;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Context mContext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public PlayStepFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_play_step, container, false);

        if(savedInstanceState != null) {
            currentRecipe = savedInstanceState.getParcelable(Utils.BUNDLE_KEY_RECIPE);
            currentStep = savedInstanceState.getInt(Utils.BUNDLE_KEY_STEP_INDEX);
        }
        recipeSteps = currentRecipe.getRecipeSteps();
        stepsSize = recipeSteps.size();

        // Initialize the player view.
        View includedLayout = rootView.findViewById(R.id.include_player);
        mPlayerView = includedLayout.findViewById(R.id.playerView);
        mContext = mPlayerView.getContext();

        if (currentRecipe != null) {
            recipeSteps = currentRecipe.getRecipeSteps();
            stepsSize = recipeSteps.size();

            if (currentStep >= 0) {
                RecipeStep currentRecipeStep = recipeSteps.get(currentStep);
                if(rootView.findViewById(R.id.tvDescription) != null) {
                    //We are in Portrait mode
                    mTextViewDescription = rootView.findViewById(R.id.tvDescription);
                    mTextViewDescription.setText(currentRecipeStep.getDescription());
                }
                launchVideo(currentRecipeStep);
            }

            //There are buttons for phone mode
            if (rootView.findViewById(R.id.btnBefore) !=null) {
                mBtnBefore = rootView.findViewById(R.id.btnBefore);
                mBtnAfter = rootView.findViewById(R.id.btnAfter);
                setButtons();
                mBtnBefore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadStepBefore();
                    }
                });
                mBtnAfter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadStepAfter();
                    }
                });
            }
        }

        // Return the root view
        return rootView;
    }


    /**
     * Initialize ExoPlayer.
     * @param mediaString The String URL of the sample to play.
     */
    private void initializePlayer(String mediaString) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);//TODO
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(mContext, "BakingApp");
            Uri mediaUri = Uri.parse(mediaString);

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    mContext, userAgent), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void loadStepBefore() {
        currentStep = currentStep -1;
        loadNewStep();
    }

    private void loadStepAfter() {
        currentStep = currentStep +1;
        loadNewStep();
    }

    private void loadNewStep() {
        mPlayerView.setPlayer(null);
        setBackImage();
        releasePlayer();
        RecipeStep currentRecipeStep = recipeSteps.get(currentStep);
        mTextViewDescription.setText(currentRecipeStep.getDescription());
        setButtons();
        launchVideo(currentRecipeStep);
    }

    private void setBackImage() {
        //TODO
        /*
        Bitmap image = BitmapFactory.decodeResource (getResources(), R.drawable.saucepan);
        mPlayerView.setUseArtwork(true);
        mPlayerView.setDefaultArtwork(image);*/
    }
    private void launchVideo(RecipeStep currentRecipeStep) {
        String thumbnailURL = currentRecipeStep.getThumbnailURL();
        String videoURL = currentRecipeStep.getVideoURL();

        if(thumbnailURL.isEmpty() && videoURL.isEmpty()) {
            //TODO
            setBackImage();
        } else {
            if(!videoURL.isEmpty()) {
                initializePlayer(videoURL);
            } else if(!thumbnailURL.isEmpty()) {
                initializePlayer(thumbnailURL);
            }
        }
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }

    private void setButtons(){
        if(currentStep == 0) {
            mBtnBefore.setEnabled(false);
            mBtnAfter.setEnabled(true);
        } else if (currentStep == stepsSize-1){
            mBtnBefore.setEnabled(true);
            mBtnAfter.setEnabled(false);
        } else {
            mBtnBefore.setEnabled(true);
            mBtnAfter.setEnabled(true);
        }
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(Utils.BUNDLE_KEY_RECIPE, currentRecipe);
        currentState.putInt(Utils.BUNDLE_KEY_STEP_INDEX, currentStep);
    }
    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

}
