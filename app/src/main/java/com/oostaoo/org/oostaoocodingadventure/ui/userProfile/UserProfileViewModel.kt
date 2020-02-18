package com.oostaoo.org.oostaoocodingadventure.ui.userProfile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class UserProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is user profile Fragment"
    }
    val text: LiveData<String> = _text
}