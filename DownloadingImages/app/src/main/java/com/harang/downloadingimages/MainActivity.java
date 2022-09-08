package com.harang.downloadingimages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

  ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    imageView = findViewById(R.id.imageView);
  }

  public void downloadImage(View view) {
    ImageDownloader task = new ImageDownloader();
    Bitmap myImage;
    try {
      myImage = task.execute("https://static.wikia.nocookie.net/simpsons/images/9/90/Kamp_Bart_Tapped_Out.png/revision/latest?cb=20150807203505").get();
      imageView.setImageBitmap(myImage);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... urls) {
      try {
        URL url = new URL(urls[0]);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream in = connection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(in);
        return myBitmap;
      } catch(Exception e) {
        e.printStackTrace();
        return null;
      }
    }
  }
}