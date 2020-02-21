package com.oostaoo.org.oostaoocodingadventure.database

import androidx.lifecycle.LiveData
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign

class DataRepository() {

    private lateinit var mDatabase: AppDatabase
    private var sInstance: DataRepository? = null

    constructor(database: AppDatabase) : this() {
        mDatabase = database
    }

    fun getInstance(database: AppDatabase?): DataRepository? {
        if (sInstance == null) {
            synchronized(DataRepository::class.java) {
                if (sInstance == null) {
                    sInstance = DataRepository(database!!)
                }
            }
        }
        return sInstance
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    fun getCampaigns(): LiveData<List<Campaign>> {
        return mDatabase.campaignDao().getCampaigns()
    }

    suspend fun insertCampaign(campaign: Campaign) {
        mDatabase.campaignDao().insert(campaign)
    }
}