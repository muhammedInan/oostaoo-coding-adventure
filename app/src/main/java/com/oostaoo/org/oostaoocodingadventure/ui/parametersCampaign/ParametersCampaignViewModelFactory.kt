package com.oostaoo.org.oostaoocodingadventure.ui.parametersCampaign

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ParametersCampaignViewModelFactory() : ViewModelProvider.Factory {

    private var mIdCampaign = 0
    private var mApplication: Application? = null

    constructor(idCampaign: Int, application: Application) : this() {

        mIdCampaign = idCampaign
        mApplication = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ParametersCampaignViewModel::class.java)) return ParametersCampaignViewModel(mIdCampaign, mApplication!!) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}