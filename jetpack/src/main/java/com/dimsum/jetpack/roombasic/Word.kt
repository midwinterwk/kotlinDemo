package com.dimsum.jetpack.roombasic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Word {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "english_word")
    lateinit var word: String
    @ColumnInfo(name = "chinese_meaning")
    lateinit var chineseMeaning: String

    constructor(word: String, chineseMeaning: String) {
        this.word = word
        this.chineseMeaning = chineseMeaning
    }


}