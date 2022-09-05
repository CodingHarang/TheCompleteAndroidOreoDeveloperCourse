package com.harang.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  Button startButton, button0, button1, button2, button3, restartButton;
  ArrayList<Integer> answers = new ArrayList<>();
  int locationOfCorrectAnswer;
  TextView resultTextView, scoreTextView, sumTextView, timerTextView;
  int score = 0, numberOfQuestions = 0;
  ConstraintLayout gameLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sumTextView = findViewById(R.id.txtQuestion);
    resultTextView = findViewById(R.id.txtResult);
    scoreTextView = findViewById(R.id.txtScore);
    button0 = findViewById(R.id.button0);
    button1 = findViewById(R.id.button1);
    button2 = findViewById(R.id.button2);
    button3 = findViewById(R.id.button3);
    startButton = findViewById(R.id.btnStart);
    timerTextView = findViewById(R.id.txtTimer);
    restartButton = findViewById(R.id.btnRestart);
    gameLayout = findViewById(R.id.gameLayout);

  }

  public void start(View view) {
    startButton.setVisibility(View.INVISIBLE);
    gameLayout.setVisibility(View.VISIBLE);
    playAgain(findViewById(R.id.btnStart));
  }

  public void chooseAnswer(View view) {
    if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
      Log.i("Correct", "Correct");
      resultTextView.setText("Correct");
      score++;
    } else {
      Log.i("Incorrect", "Incorrect");
      resultTextView.setText("Incorrect");
    }
    numberOfQuestions++;
    scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
    newQuestion();


  }

  public void newQuestion() {
    Random rand = new Random();

    int a = rand.nextInt(21);
    int b = rand.nextInt(21);

    sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

    locationOfCorrectAnswer = rand.nextInt(4);
    answers.clear();

    for(int i = 0; i < 4; i++) {
      if(i == locationOfCorrectAnswer) {
        answers.add(a + b);
      } else {
        int wrongAnswer = rand.nextInt(41);
        while(wrongAnswer == (a + b)) {
          wrongAnswer = rand.nextInt(41);
        }
        answers.add(wrongAnswer);
      }
    }
    button0.setText(Integer.toString(answers.get(0)));
    button1.setText(Integer.toString(answers.get(1)));
    button2.setText(Integer.toString(answers.get(2)));
    button3.setText(Integer.toString(answers.get(3)));
  }

  public void playAgain(View view) {
    score = 0;
    numberOfQuestions = 0;
    timerTextView.setText("30s");
    newQuestion();
    scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
    restartButton.setVisibility(View.INVISIBLE);
    resultTextView.setText("Choose your answer");

    new CountDownTimer(30100, 1000) {
      @Override
      public void onTick(long l) {
        timerTextView.setText(String.valueOf(l / 1000) + "s");
      }

      @Override
      public void onFinish() {
        resultTextView.setText("Done");
        restartButton.setVisibility(View.VISIBLE);
      }
    }.start();
  }
}