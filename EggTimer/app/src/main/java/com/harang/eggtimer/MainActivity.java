package com.harang.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  TextView timerTextView;
  SeekBar timerSeekBar;
  boolean isCounterActive = false;
  Button button;
  CountDownTimer countDownTimer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    timerSeekBar = findViewById(R.id.seekBar);
    timerTextView = findViewById(R.id.textView);

    timerSeekBar.setMax(600);
    timerSeekBar.setProgress(30);
    button = findViewById(R.id.button);

    timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        updateTimer(i);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
  }

  public void ButtonClicked(View view) {
    if(isCounterActive) {
      resetTimer();
    } else {
      isCounterActive = true;
      timerSeekBar.setEnabled(false);
      button.setText("Stop");

      countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
        @Override
        public void onTick(long l) {
          updateTimer((int) l / 1000);
        }

        @Override
        public void onFinish() {
          MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
          mPlayer.start();
          resetTimer();
        }
      }.start();
    }
  }

  public void updateTimer(int secondsLeft) {
    int minutes = secondsLeft / 60;
    int seconds = secondsLeft - minutes * 60;

    String secondString = Integer.toString(seconds);

    if(seconds <= 9) {
      secondString = "0" + secondString;
    }

    timerTextView.setText(Integer.toString(minutes) + " : " + secondString);
  }

  public void resetTimer() {
    timerTextView.setText("0 : 30");
    timerSeekBar.setProgress(30);
    timerSeekBar.setEnabled(true);
    countDownTimer.cancel();
    button.setText("Start");
    isCounterActive = false;
  }
}