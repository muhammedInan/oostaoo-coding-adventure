package com.oostaoo.org.oostaoocodingadventure.ui.facturation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class FacturationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is facturation Fragment"
    }
    val text: LiveData<String> = _text
}