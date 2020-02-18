package com.oostaoo.org.oostaoocodingadventure.ui.myTests

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MyTestsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my tests Fragment"
    }
    val text: LiveData<String> = _text
}