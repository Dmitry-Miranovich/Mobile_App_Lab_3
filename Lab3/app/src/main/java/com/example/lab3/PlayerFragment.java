package com.example.lab3;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean isPaused = true;
    private MediaPlayer player;
    private SeekBar audioControl;
    private AudioManager audioManager;
    private final Handler handler = new Handler();
    private final Runnable updatePositionRunnable = new Runnable() {
        @Override
        public void run() {
            updatePosition();
        }
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView startButton = view.findViewById(R.id.start_button);
        ImageView nextButton = view.findViewById(R.id.next_button);
        ImageView prevButton = view.findViewById(R.id.prev_button);
        player = MediaPlayer.create(getContext(), R.raw.file1);
        audioControl = view.findViewById(R.id.music_slider);
        audioControl.setMax(player.getDuration());
        audioControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(player.isPlaying() && b){
                    player.seekTo(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isPaused = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isPaused = false;
            }
        });
        player.setOnCompletionListener(mediaPlayer -> {
            stopPlay();
        });
        startButton.setOnClickListener(view1 -> {
            if(isPaused){
                startButton.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                player.start();
                isPaused = false;
                updatePosition();
            }else{
                startButton.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                player.pause();
                handler.removeCallbacks(updatePositionRunnable);
                isPaused = true;
            }
        });
        //todo Reword music player
        nextButton.setOnClickListener(view1 -> {
            player.reset();
            updatePosition();
            isPaused = true;
        });
        /*
        prevButton.setOnClickListener(view1 ->{
            player.seekTo(player.getDuration());
            updatePosition();
            isPaused = false;
        });

         */
    }
    private void stopPlay(){
        player.stop();
        try{
            player.prepare();
            player.seekTo(0);
        }catch(Exception ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void updatePosition(){
        handler.removeCallbacks(updatePositionRunnable);
        audioControl.setProgress(player.getCurrentPosition());
        handler.postDelayed(updatePositionRunnable, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player.isPlaying()){
            stopPlay();
        }
    }
}