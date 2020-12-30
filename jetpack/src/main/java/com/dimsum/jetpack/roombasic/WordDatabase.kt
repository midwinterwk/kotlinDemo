package com.dimsum.jetpack.roombasic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordDatabase: RoomDatabase() {
    companion object {
        private lateinit var mContext: Context
        private var INSTANCE: WordDatabase? = null
            get() {
                if (field == null) field = Room.databaseBuilder(mContext, WordDatabase::class.java, "word_database")
                    .build()
                return field
            }

        @Synchronized
        fun getWordDatabase(context: Context): WordDatabase{
            mContext = context
            return INSTANCE!!
        }
    }
    abstract fun getWordDao(): WordDao
}