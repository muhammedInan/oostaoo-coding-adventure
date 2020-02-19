package com.oostaoo.org.oostaoocodingadventure.ui.newTest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewTestViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is new test Fragment"
    }
    val text: LiveData<String> = _text
}