package com.oostaoo.org.oostaoocodingadventure.database.campaign

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CampaignDao {

    @Query("SELECT * from campaign_table ORDER BY idCampaign ASC")
    fun getCampaigns(): LiveData<List<Campaign>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(campaign: Campaign)

    @Query("DELETE FROM campaign_table")
    suspend fun deleteAll()
}