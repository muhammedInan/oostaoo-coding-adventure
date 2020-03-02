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

    @Query("SELECT * from campaign_table WHERE idCampaign = :idCampaign")
    fun getCampaign(idCampaign: Int): LiveData<Campaign>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(campaign: Campaign)

    @Query("DELETE FROM campaign_table")
    suspend fun deleteAll()
}