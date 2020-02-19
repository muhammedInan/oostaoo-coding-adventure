package com.oostaoo.org.oostaoocodingadventure.ui.dataProtection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataProtectionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is data protection Fragment"
    }
    val text: LiveData<String> = _text
}