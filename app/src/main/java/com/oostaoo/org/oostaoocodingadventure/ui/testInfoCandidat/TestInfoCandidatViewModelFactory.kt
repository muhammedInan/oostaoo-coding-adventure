package com.oostaoo.org.oostaoocodingadventure.ui.testCandidats

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oostaoo.org.oostaoocodingadventure.ui.testInfoCandidat.TestInfoCandidatViewModel

@Suppress("UNCHECKED_CAST")
class TestInfoCandidatViewModelFactory() : ViewModelProvider.Factory, Parcelable {

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
        if (modelClass.isAssignableFrom(TestInfoCandidatViewModel::class.java)) return TestInfoCandidatViewModel(mIdCampaign, mApplication!!) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mIdCampaign)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestInfoCandidatViewModelFactory> {
        override fun createFromParcel(parcel: Parcel): TestInfoCandidatViewModelFactory {
            return TestInfoCandidatViewModelFactory(parcel)
        }

        override fun newArray(size: Int): Array<TestInfoCandidatViewModelFactory?> {
            return arrayOfNulls(size)
        }
    }
}