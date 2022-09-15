/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.harang.parsestarter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity {

  Boolean signUpModeActive = true;
  TextView txtLogin;
  EditText edtUsername, edtPassword;
  ImageView imgLogo;
  ConstraintLayout backgroundLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    txtLogin = findViewById(R.id.txtLogin);
    edtUsername = findViewById(R.id.edtUsername);
    edtPassword = findViewById(R.id.edtPassword);
    imgLogo = findViewById(R.id.imgLogo);
    backgroundLayout = findViewById(R.id.backgroundLayout);
    imgLogo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onClicked(view);
      }
    });

    backgroundLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onClicked(view);
      }
    });

    txtLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onClicked(view);
      }
    });

    edtPassword.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
          signUpClicked(view);
        }
        return false;
      }
    });

    if(ParseUser.getCurrentUser() != null) {
      showUserList();
    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void signUpClicked(View view) {

    if(edtUsername.getText().toString().matches("") || edtPassword.getText().toString().matches("")) {
      Toast.makeText(this, "A username and a password are required.", Toast.LENGTH_SHORT).show();
    } else {
      if(signUpModeActive) {
        ParseUser user = new ParseUser();
        user.setUsername(edtUsername.getText().toString());
        user.setPassword(edtPassword.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("Signup", "Success");
              showUserList();
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      } else {
        ParseUser.logInInBackground(edtUsername.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if(user != null) {
              Log.i("Login", "ok");
              showUserList();
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    }
  }

  public void onClicked(View view) {
    if(view.getId() == R.id.txtLogin) {
      Button btnSignup = findViewById(R.id.btnSignup);
      if(signUpModeActive) {
        signUpModeActive = false;
        btnSignup.setText("Login");
        txtLogin.setText("or, Signup");
      } else {
        signUpModeActive = true;
        btnSignup.setText("Sign Up");
        txtLogin.setText("or, Login");
      }
    } else if(view.getId() == R.id.imgLogo || view.getId() == R.id.backgroundLayout) {
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
  }

  public void showUserList() {
    Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
    startActivity(intent);
  }
}