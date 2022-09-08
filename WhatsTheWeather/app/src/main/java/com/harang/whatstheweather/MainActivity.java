package com.harang.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

  EditText editText;
  TextView resultTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    editText = findViewById(R.id.editText);
    resultTextView = findViewById(R.id.resultTextView);
  }

  public class DownloadTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
      String result = "";
      URL url;
      HttpURLConnection urlConnection = null;

      try {
        url = new URL(urls[0]);
        urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = urlConnection.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        int data = reader.read();

        while(data != -1) {
          char current = (char) data;
          result += current;
          data = reader.read();
        }
        return result;
      } catch(Exception e) {
        e.printStackTrace();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
          }
        }, 0);
        return null;
      }
    }

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);

      try {
        JSONObject jsonObject = new JSONObject(s);
        String weatherInfo = jsonObject.getString("weather");
        Log.i("Weather content", weatherInfo);
        JSONArray arr = new JSONArray(weatherInfo);
        String message = "";

        for(int i = 0; i < arr.length(); i++) {
          JSONObject jsonPart = arr.getJSONObject(i);

          String main, description;
          main = jsonPart.getString("main");
          description = jsonPart.getString("description");

          if(!main.equals("") && !description.equals("")) {
            message += main + ": " + description + "\r\n";
          }
//          Log.i("main", jsonPart.getString("main"));
//          Log.i("description", jsonPart.getString("description"));
        }

        if(!message.equals("")) {
          resultTextView.setText(message);
        } else {
          Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
        }
      } catch(Exception e) {
        e.printStackTrace();
        Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
      }
    }
  }

  public void getWeather(View view) {
    try {
      DownloadTask task = new DownloadTask();
      String encodedCityName = URLEncoder.encode(editText.getText().toString(), "UTF-8");
      task.execute("https://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=31b6515f6b1688b947cd1d13c21e193d");

      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    } catch(Exception e) {
      e.printStackTrace();
      Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
    }
  }
}