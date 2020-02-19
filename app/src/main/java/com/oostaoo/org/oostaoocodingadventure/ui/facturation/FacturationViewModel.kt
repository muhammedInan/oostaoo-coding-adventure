package com.oostaoo.org.oostaoocodingadventure.ui.facturation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FacturationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is facturation Fragment"
    }
    val text: LiveData<String> = _text
}