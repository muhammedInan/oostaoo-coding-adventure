package com.oostaoo.org.oostaoocodingadventure.ui.listQuestions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListQuestionsViewModelFactory() : ViewModelProvider.Factory {

    private var mTechnologiesName = ArrayList<String>()
    private var mApplication: Application? = null

    constructor(technologiesName: ArrayList<String>, application: Application) : this() {
        mTechnologiesName = technologiesName
        mApplication = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListQuestionsViewModel::class.java)) {
            return ListQuestionsViewModel(mTechnologiesName, mApplication!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}