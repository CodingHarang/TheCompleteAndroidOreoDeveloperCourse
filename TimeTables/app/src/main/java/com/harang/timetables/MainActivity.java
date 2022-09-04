package com.harang.timetables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  ListView timesTablesListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SeekBar timesTablesSeekBar = findViewById(R.id.timesTablesSeekBar);
    timesTablesListView = findViewById(R.id.timesTablesListView);

    int max = 20;
    int startingPosition = 10;

    timesTablesSeekBar.setMax(max);
    timesTablesSeekBar.setProgress(startingPosition);

    generateTimesTable(startingPosition);

    timesTablesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int min = 1;
        int timesTableNumber;

        if(i < min) {
          timesTableNumber = min;
        } else {
          timesTableNumber = i;
        }
        Log.i("Seekbar Value", Integer.toString(timesTableNumber));
        generateTimesTable(timesTableNumber);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
  }

  public void generateTimesTable(int timesTableNumber) {
    ArrayList<String> timesTableContent = new ArrayList<>();
    for(int j = 1; j <= 100; j++) {
      timesTableContent.add(Integer.toString(j * timesTableNumber));
    }
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timesTableContent);
    timesTablesListView.setAdapter(arrayAdapter);
  }
}