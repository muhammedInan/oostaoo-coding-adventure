package com.oostaoo.org.oostaoocodingadventure.ui.users

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class UsersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is users Fragment"
    }
    val text: LiveData<String> = _text
}