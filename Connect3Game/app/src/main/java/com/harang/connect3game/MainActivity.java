package com.harang.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  int activePlayer = 0;
  int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
  int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
  boolean gameActive = true;

  public void dropIn(View view) {
    ImageView counter = (ImageView) view;
    int tappedCounter = Integer.parseInt(counter.getTag().toString());
    if(gameState[tappedCounter] == 2 && gameActive) {
      gameState[tappedCounter] = activePlayer;
      counter.setTranslationY(-1500);

      if (activePlayer == 0) {
        counter.setImageResource(R.drawable.yellow);
        activePlayer = 1;
      } else {
        counter.setImageResource(R.drawable.red);
        activePlayer = 0;
      }
      counter.animate().translationYBy(1500).setDuration(300);

      for (int[] winningPosition : winningPositions) {
        if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
          String winner = "";
          gameActive = false;

          if (activePlayer == 1) {
            winner = "Yellow";
          } else {
            winner = "Red";
          }

//          Toast.makeText(this, winner + " has won!", Toast.LENGTH_SHORT).show();
          Button restartButton = (Button) findViewById(R.id.restartButton);
          TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
          winnerTextView.setText(winner + " has won!");
          restartButton.setVisibility(View.VISIBLE);
          winnerTextView.setVisibility(View.VISIBLE);
        }
      }
    }
  }

  public void Restart(View view) {
    Button restartButton = (Button) findViewById(R.id.restartButton);
    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
    restartButton.setVisibility(View.INVISIBLE);
    winnerTextView.setVisibility(View.INVISIBLE);

    GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
    for(int i = 0; i < gridLayout.getChildCount(); i++) {
      ImageView child = (ImageView) gridLayout.getChildAt(i);
      child.setImageDrawable(null);
    }

    activePlayer = 0;
    for(int i = 0; i < gameState.length; i++) {
      gameState[i] = 2;
    }
    gameActive = true;
  }
}