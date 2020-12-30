package com.dimsum.jetpack.roombasic

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private var wordRepository: WordRepository = WordRepository(application)

    fun getAllWordsLive(): LiveData<List<Word>> {
        return wordRepository.getAllWordsLive()
    }

    fun insertWords(vararg words: Word?) {
        wordRepository.insertWords(*words)
    }

    fun updateWords(vararg words: Word?) {
        wordRepository.updateWords(*words)
    }

    fun deleteWords(vararg words: Word?) {
        wordRepository.deleteWords(*words)
    }

    fun deleteAllWords() {
        wordRepository.deleteAllWords()
    }
}