package com.harang.kotlinfun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

  var count = 0
  lateinit var textView: TextView
  lateinit var btnReset: Button
  lateinit var btnPlusOne: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)



    textView = findViewById<TextView>(R.id.textView)
    textView.text = "Hello Harang!"

    btnReset = findViewById(R.id.reset)
    btnPlusOne = findViewById(R.id.plusOne)

    btnReset.setOnClickListener {
      count = 0
      textView.setText(count.toString())
    }

    btnPlusOne.setOnClickListener {
      count++
      textView.setText(count.toString())
    }
  }
}