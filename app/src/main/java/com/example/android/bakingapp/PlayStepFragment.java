package com.example.android.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.utils.Utils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

/**
 * Created by vanessa on 17/02/2019.
 */

public class PlayStepFragment extends Fragment implements ExoPlayer.EventListener {

    private Recipe currentRecipe;
    private List<RecipeStep> recipeSteps;
    private int currentStep;
    private int stepsSize;
    private Button mBtnBefore;
    private Button mBtnAfter;
    private TextView mTextViewDescription;

    //For ExoPlayer
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Context mContext;
    private View mIncludedLayout;
    private ImageView mImageView;

    //For mediaSession
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mBuilder;
    private static final String TAG = PlayStepFragment.class.getSimpleName();

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
        mIncludedLayout = rootView.findViewById(R.id.include_player);
        mPlayerView = mIncludedLayout.findViewById(R.id.playerView);
        mImageView = mIncludedLayout.findViewById(R.id.playerViewImage);

        mContext = mPlayerView.getContext();

        initializeMediaSession(); //TODO here?

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

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(mContext, "tag_media_session");
        mMediaSession.setFlags((MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS));
        mMediaSession.setMediaButtonReceiver(null);
        mBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
        mMediaSession.setPlaybackState(mBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
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
            mExoPlayer.addListener(this);

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
        //setBackImage();
        releasePlayer();
        RecipeStep currentRecipeStep = recipeSteps.get(currentStep);
        mTextViewDescription.setText(currentRecipeStep.getDescription());
        setButtons();
        launchVideo(currentRecipeStep);
    }

    private void setBackImage() {
        //TODO
        mImageView.setVisibility(View.VISIBLE);
        mPlayerView.setVisibility(View.GONE);
        /*Bitmap image = BitmapFactory.decodeResource (getResources(), R.drawable.saucepan);
        mPlayerView.setUseArtwork(true);
        mPlayerView.setDefaultArtwork(image);*/
    }

    private void removeBackImage() {
        mImageView.setVisibility(View.GONE);
        mPlayerView.setVisibility(View.VISIBLE);
    }
    private void launchVideo(RecipeStep currentRecipeStep) {
        String thumbnailURL = currentRecipeStep.getThumbnailURL();
        String videoURL = currentRecipeStep.getVideoURL();

        if(thumbnailURL.isEmpty() && videoURL.isEmpty()) {
            //TODO
            setBackImage();
        } else {
            removeBackImage();
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
        mMediaSession.setActive(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            // TODO (3): When ExoPlayer is playing, update the PlayBackState.
            Log.d(TAG, "onPlayerStateChanged: PLAYING");

            mBuilder.setState(PlaybackStateCompat.STATE_PLAYING, mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            // TODO (3): When ExoPlayer is paused, update the PlayBackState.
            Log.d(TAG, "onPlayerStateChanged: PAUSED");

            mBuilder.setState(PlaybackStateCompat.STATE_PAUSED, mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
