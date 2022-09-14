/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.harang.parsestarter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    ParseObject score = new ParseObject("Score");
//    score.put("username", "sean");
//    score.put("score", 55);
//    score.saveInBackground(new SaveCallback() {
//      @Override
//      public void done(ParseException e) {
//        if(e == null) {
//          Log.i("Success", "We saved the score");
//        } else {
//          e.printStackTrace();
//        }
//      }
//    });


//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//    query.getInBackground("bXO68vYM3h", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject object, ParseException e) {
//        if(e == null && object != null) {
//          object.put("score", 85);
//          object.saveInBackground();
//          Log.i("username", object.getString("username"));
//          Log.i("score", "" + object.getInt("score"));
//        } else {
//          e.printStackTrace();
//        }
//      }
//    });


//    ParseObject tweet = new ParseObject("Tweet");
//    tweet.put("username", "harang");
//    tweet.put("tweet", "I like game");
//    tweet.saveInBackground(new SaveCallback() {
//      @Override
//      public void done(ParseException e) {
//        if(e == null) {
//          Log.i("OK", "success");
//        } else {
//          e.printStackTrace();
//        }
//      }
//    });
//
//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
//    query.getInBackground("pgmlHkRtjd", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject object, ParseException e) {
//        if(e == null && object != null) {
//          object.put("tweet", "I like study");
//          object.saveInBackground();
//          Log.i("username", object.getString("username"));
//          Log.i("tweet", object.getString("tweet"));
//        } else {
//          e.printStackTrace();
//        }
//      }
//    });

//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//    query.whereEqualTo("username", "sean");
//    query.setLimit(1);
//
//    query.findInBackground(new FindCallback<ParseObject>() {
//      @Override
//      public void done(List<ParseObject> objects, ParseException e) {
//        if(e == null) {
//          if(objects.size() > 0) {
//            for(ParseObject object: objects) {
//              Log.i("username", object.getString("username"));
//              Log.i("score", Integer.toString(object.getInt("score")));
//            }
//          }
//        }
//      }
//    });


    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.whereGreaterThan("score", 50);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e == null && objects != null) {
          for(ParseObject score: objects) {
            score.put("score", score.getInt("score") + 20);
            score.saveInBackground();
          }
        }
      }
    });
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}