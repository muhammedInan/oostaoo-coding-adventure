package com.oostaoo.org.oostaoocodingadventure.ui.newTest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class NewTestViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is new test Fragment"
    }
    val text: LiveData<String> = _text
}