package com.oostaoo.org.oostaoocodingadventure.ui.configEmail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConfigEmailViewModelFactory() : ViewModelProvider.Factory {

    private var mIdCampaign = 0
    private var mNames = ArrayList<String>()
    private var mEmails = ArrayList<String>()
    private var mApplication: Application? = null

    constructor(idCampaign: Int, names: ArrayList<String>, emails: ArrayList<String>, application: Application) : this() {
        mIdCampaign = idCampaign
        mNames = names
        mEmails = emails
        mApplication = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigEmailViewModel::class.java)) return ConfigEmailViewModel(mIdCampaign, mNames, mEmails, mApplication!!) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}