package com.oostaoo.org.oostaoocodingadventure.ui.testCandidats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TestCandidatsViewModelFactory() : ViewModelProvider.Factory {

    private var mIdCampaign = 0

    constructor(idCampaign: Int) : this() {
        mIdCampaign = idCampaign
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestCandidatsViewModel::class.java)) {
            return TestCandidatsViewModel(mIdCampaign) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}