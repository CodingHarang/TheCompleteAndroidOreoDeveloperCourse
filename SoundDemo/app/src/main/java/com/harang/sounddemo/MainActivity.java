package com.harang.sounddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

  MediaPlayer mediaPlayer;
  AudioManager audioManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

    mediaPlayer = MediaPlayer.create(this, R.raw.light_rain);
    SeekBar volumeControl = findViewById(R.id.volumeSeekBar);

    volumeControl.setMax(maxVolume);
    volumeControl.setProgress(currentVolume);

    volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    SeekBar progressSeekBar = (SeekBar) findViewById(R.id.progressSeekBar);
    progressSeekBar.setMax(mediaPlayer.getDuration());
    progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mediaPlayer.seekTo(i);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    new Timer().scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        progressSeekBar.setProgress(mediaPlayer.getCurrentPosition());
      }
    }, 0, 1000);
  }


  public void play(View view) {
    mediaPlayer.start();
  }

  public void pause(View view) {
    mediaPlayer.pause();
  }
}