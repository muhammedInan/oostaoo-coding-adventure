package com.oostaoo.org.oostaoocodingadventure.ui.testCandidats

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TestCandidatsViewModelFactory() : ViewModelProvider.Factory, Parcelable {

    private var mIdCampaign = 0
    private var mApplication: Application? = null

    constructor(parcel: Parcel) : this() {
        mIdCampaign = parcel.readInt()
    }

    constructor(idCampaign: Int, application: Application) : this() {
        mIdCampaign = idCampaign
        mApplication = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestCandidatsViewModel::class.java)) return TestCandidatsViewModel(mIdCampaign, mApplication!!) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mIdCampaign)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestCandidatsViewModelFactory> {
        override fun createFromParcel(parcel: Parcel): TestCandidatsViewModelFactory {
            return TestCandidatsViewModelFactory(parcel)
        }

        override fun newArray(size: Int): Array<TestCandidatsViewModelFactory?> {
            return arrayOfNulls(size)
        }
    }
}