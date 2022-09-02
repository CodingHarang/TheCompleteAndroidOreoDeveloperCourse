package com.harang.media;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

  boolean bartIsShowing = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ImageView imageView1 = findViewById(R.id.imageView1);

    imageView1.animate().translationXBy(-1000);

  }

  public void fade(View view) {
    Log.i("Info", "ImageView tapped");
    ImageView imageView1 = findViewById(R.id.imageView1);
    ImageView imageView2 = findViewById(R.id.imageView2);

    imageView1.animate().rotationBy(90).setDuration(1000);
  }
}