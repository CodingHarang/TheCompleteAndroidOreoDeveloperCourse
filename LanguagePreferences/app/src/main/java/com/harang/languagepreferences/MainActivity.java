package com.harang.languagepreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  SharedPreferences sharedPreferences;
  TextView textView;

  @Override
  public boolean onCreateOptionsMenu(@NonNull Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.main_menu, menu);

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    super.onOptionsItemSelected(item);

    switch(item.getItemId()) {
      case R.id.english:
        setLanguage("English");
        return true;
      case R.id.spanish:
        setLanguage("Spanish");
        return true;
      default:
        return false;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = findViewById(R.id.textView);

    sharedPreferences = this.getSharedPreferences("com.harang.languagepreferences", Context.MODE_PRIVATE);
    String language = sharedPreferences.getString("language", "Error");

    if(language.equals("Error")) {
      new AlertDialog.Builder(this)
              .setIcon(android.R.drawable.ic_btn_speak_now)
              .setTitle("Choose a language")
              .setMessage("Which language would you like to use?")
              .setPositiveButton("English", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  // Set English
                  setLanguage("English");
                }
              })
              .setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  // Set Spanish
                  setLanguage("English");
                }
              })
              .show();
    } else {
      textView.setText(language);
    }
  }

  public void setLanguage(String language) {
    sharedPreferences.edit().putString("language", language).apply();
    textView.setText(language);
  }
}