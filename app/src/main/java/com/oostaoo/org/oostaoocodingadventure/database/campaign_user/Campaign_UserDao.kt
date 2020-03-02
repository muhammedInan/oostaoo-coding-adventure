package com.oostaoo.org.oostaoocodingadventure.database.campaign_user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Campaign_UserDao {

    @Query("SELECT * from campaign_user_table ORDER BY idCampaign_User ASC")
    fun getCampaign_Users(): LiveData<List<Campaign_User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(campaign_User: Campaign_User)

    @Query("DELETE FROM campaign_user_table")
    suspend fun deleteAll()
}