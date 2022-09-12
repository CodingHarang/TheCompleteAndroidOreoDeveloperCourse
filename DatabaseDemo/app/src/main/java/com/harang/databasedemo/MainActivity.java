package com.harang.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    try {
      SQLiteDatabase sqliteDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
//      sqliteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
//      sqliteDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Nick', 20)");
//      sqliteDatabase.execSQL("INSERT INTO users (name, age) VALUES ('John', 22)");

      Cursor c = sqliteDatabase.rawQuery("SELECT * FROM users WHERE age < 21", null);
      int nameIndex = c.getColumnIndex("name");
      int ageIndex = c.getColumnIndex("age");
      c.moveToFirst();
      while(c != null) {
        Log.i("Results - name", c.getString(nameIndex));
        Log.i("Results - age", "" + c.getInt(ageIndex));
        c.moveToNext();
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
//    SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
//    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
//    myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Nick', 28)");
//    myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('John', 33)");
//
//    Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);
//
//    int nameIndex = c.getColumnIndex("name");
//    int ageIndex = c.getColumnIndex("age");
//    c.moveToFirst();
//
//    while(c != null) {
//      Log.i("name", c.getString(nameIndex) + "  " + c.getString(ageIndex));
//      c.moveToNext();
//    }
  }
}