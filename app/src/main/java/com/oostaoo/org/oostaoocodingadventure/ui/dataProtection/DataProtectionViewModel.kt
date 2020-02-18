package com.oostaoo.org.oostaoocodingadventure.ui.dataProtection

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class DataProtectionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is data protection Fragment"
    }
    val text: LiveData<String> = _text
}