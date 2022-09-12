package com.harang.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences sharedPreferences = this.getSharedPreferences("com.harang.sharedpreferences", Context.MODE_PRIVATE);
    //sharedPreferences.edit().putString("username","harang").apply();
    ArrayList<String> friends = new ArrayList<>();

    friends.add("Fido");
    friends.add("Sarah");
    friends.add("Jones");

    try {
      sharedPreferences.edit().putString("friends", ObjectSerializer.serialize(friends)).apply();

      Log.i("friends", ObjectSerializer.serialize(friends));
    } catch (IOException e) {
      e.printStackTrace();
    }

    ArrayList<String> newFriends = new ArrayList<>();
    try {
      newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends", ObjectSerializer.serialize(new ArrayList<>())));
    } catch (IOException e) {
      e.printStackTrace();
    }

    Log.i("new Friends", newFriends.toString());
//    String username = sharedPreferences.getString("username", "");
//
//    Log.i("preference", username);
  }
}