package com.oostaoo.org.oostaoocodingadventure.ui.listQuestions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListQuestionsViewModelFactory() : ViewModelProvider.Factory {

    private var mName = ""
    private var mLevel = ""
    private var mLangs = ""
    private var mCopy_paste = false
    private var mSent_report = false
    private var mProfile = 0
    private var mUser = 0
    private var mTechnologiesId = ArrayList<Int>()
    private var mTechnologiesName = ArrayList<String>()
    private var mApplication: Application? = null

    constructor(Name: String, level: String, langs: String, copy_paste: Boolean, sent_report: Boolean,
                profile: Int, user: Int, technologiesId: ArrayList<Int>, technologiesName: ArrayList<String>,
                application: Application) : this() {
        mName = Name
        mLevel = level
        mLangs = langs
        mCopy_paste = copy_paste
        mSent_report = sent_report
        mProfile = profile
        mUser = user
        mTechnologiesId = technologiesId
        mTechnologiesName = technologiesName
        mApplication = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListQuestionsViewModel::class.java)) {
            return ListQuestionsViewModel(mName, mLevel, mLangs, mCopy_paste, mSent_report, mProfile, mUser, mTechnologiesId, mTechnologiesName, mApplication!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}