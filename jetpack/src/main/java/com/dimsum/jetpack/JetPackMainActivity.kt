package com.dimsum.jetpack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimsum.jetpack.roombasic.RoomMainActivity
import kotlinx.android.synthetic.main.activity_jetpack_main.*

class JetPackMainActivity : AppCompatActivity() {

    private var ctx: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_main)
        ctx = this
        rv_jetPack.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val names = arrayOf("ROOM")
        val mAdapter = JetPackAdapter(names)
        rv_jetPack.adapter = mAdapter
        mAdapter.setOnItemClick(View.OnClickListener { v ->
            when (v.tag as String) {
                    "ROOM" -> {
                        intent = Intent(ctx, RoomMainActivity::class.java)
                    }
                }
                startActivity(intent)
        })
//        mAdapter.setOnItemClick(object : JetPackAdapter.OnItemClick {
//            override fun setOnItemClick(view: View?, tag: String) {
//                when (tag) {
//                    "ROOM" -> {
//                        intent = Intent(ctx, RoomMainActivity::class.java)
//                    }
//                }
//                startActivity(intent)
//            }
//        })
    }

}