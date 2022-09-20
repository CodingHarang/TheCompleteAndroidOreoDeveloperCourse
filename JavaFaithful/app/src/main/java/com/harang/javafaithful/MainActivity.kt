package com.harang.javafaithful

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harang.javafaithful.R
import com.harang.javafaithful.Dog

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val myDog = Dog("Nick", 5)
  }
}