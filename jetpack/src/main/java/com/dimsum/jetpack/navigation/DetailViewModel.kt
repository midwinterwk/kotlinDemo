package com.dimsum.jetpack.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    private var number: MutableLiveData<Int> ?= null

    fun getNumber(): MutableLiveData<Int>? {
        if (number == null) {
            number = MutableLiveData(0)
        }
        return number
    }

    fun add(x:Int) {
        number?.apply {
            value = value?.plus(x)
            if (value!! < 0) value = 0
        }
    }
}