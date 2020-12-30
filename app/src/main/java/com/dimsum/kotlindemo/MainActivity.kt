package com.dimsum.kotlindemo

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dimsum.designpattern.DesignMainActivity
import com.dimsum.jetpack.JetPackMainActivity
import com.dimsum.writinglibrary.WritingMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_view_main.let {
            it.adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayListOf<String>("Writing Demo", "Design Pattern", "JetPack")
            )
            it.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
                val title = (view as TextView).text
                when (title) {
                    "Writing Demo" -> {
                        intent = Intent(this@MainActivity, WritingMainActivity::class.java)
                    }
                    "Design Pattern" -> intent = Intent(this@MainActivity, DesignMainActivity::class.java)
                    "JetPack" -> intent = Intent(this, JetPackMainActivity::class.java)
                }
                startActivity(intent)
            }
        }
    }
}