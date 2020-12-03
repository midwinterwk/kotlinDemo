package com.dimsum.kotlindemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimsum.writinglibrary.WritingMainActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent = Intent(this@MainActivity, WritingMainActivity::class.java)
        startActivity(intent)
    }
}