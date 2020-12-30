package com.dimsum.jetpack.roombasic

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class WordRepository(context: Context) {
    private var wordDao: WordDao
    private var allWordsLive: LiveData<List<Word>>

    init {
        val wordDatabase: WordDatabase = WordDatabase.getWordDatabase(context.applicationContext)
        wordDao = wordDatabase.getWordDao()
        allWordsLive = wordDao.getAllWordsLive()
    }

    fun getAllWordsLive(): LiveData<List<Word>> {
        return allWordsLive
    }


    fun insertWords(vararg words: Word?) {
        InsertAsyncTask(wordDao).execute(*words)
    }

    fun updateWords(vararg words: Word?) {
        UpdateAsyncTask(wordDao).execute(*words)
    }

    fun deleteWords(vararg words: Word?) {
        DeleteAsyncTask(wordDao).execute(*words)
    }

    fun deleteAllWords() {
        DeleteAllAsyncTask(wordDao).execute()
    }

    inner class InsertAsyncTask(val mWordDao: WordDao) : AsyncTask<Word, Unit, Unit>() {
        override fun doInBackground(vararg words: Word?) {
            mWordDao?.insertWords(*words)
        }
    }

    inner class UpdateAsyncTask(val mWordDao: WordDao) : AsyncTask<Word, Unit, Unit>() {
        override fun doInBackground(vararg words: Word?) {
            mWordDao?.updateWords(*words)
        }
    }

    inner class DeleteAsyncTask(val mWordDao: WordDao) : AsyncTask<Word, Unit, Unit>() {
        override fun doInBackground(vararg words: Word?) {
            mWordDao?.deleteWords(*words)
        }
    }

    inner class DeleteAllAsyncTask(val mWordDao: WordDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            mWordDao.deleteAllWords()
        }
    }



}