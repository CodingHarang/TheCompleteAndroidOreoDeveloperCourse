package com.harang.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences sharedPreferences = this.getSharedPreferences("com.harang.sharedpreferences", Context.MODE_PRIVATE);
    sharedPreferences.edit().putString("username","harang").apply();
    String username = sharedPreferences.getString("username", "");

    Log.i("preference", username);
  }
}