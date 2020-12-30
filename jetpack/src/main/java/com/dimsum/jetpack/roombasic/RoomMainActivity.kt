package com.dimsum.jetpack.roombasic

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimsum.jetpack.R
import kotlinx.android.synthetic.main.activity_room_main.*


class RoomMainActivity : AppCompatActivity() {
    private lateinit var wordAdapter: WordAdapter
    private lateinit var wordCardAdapter: WordAdapter
    var wordViewModel: WordViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_main)
        wordAdapter = WordAdapter(false)
        wordCardAdapter = WordAdapter(true)
        rv_word.layoutManager = LinearLayoutManager(this)
        rv_word.adapter = wordAdapter
        switch1.setOnCheckedChangeListener { _: CompoundButton, isCheck: Boolean ->
            rv_word.adapter = if (isCheck) wordCardAdapter else wordAdapter
        }

        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        wordViewModel?.getAllWordsLive()?.observe(this, Observer { words ->
            wordAdapter.setAllWords(words)
            wordAdapter.notifyDataSetChanged()
            wordCardAdapter.setAllWords(words)
            wordCardAdapter.notifyDataSetChanged()
        })

        button_Insert.setOnClickListener {
            var word = Word("Hello", "你好！")
            var word2 = Word("World", "世界！")
            wordViewModel?.insertWords(word, word2)
        }

        button_clear.setOnClickListener {
            wordViewModel?.deleteAllWords()
        }
    }
}