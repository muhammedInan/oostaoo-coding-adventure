package com.oostaoo.org.oostaoocodingadventure.database.campaign_question

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Campaign_QuestionDao {

    @Query("SELECT * from campaign_question_table ORDER BY idCampaign ASC")
    fun getCampaigns(): LiveData<List<Campaign_Question>>

    @Query("SELECT * from campaign_table WHERE idCampaign = :idCampaign")
    fun getCampaign_Question(idCampaign: Int): LiveData<Campaign_Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(campaign: Campaign_Question)

    @Query("DELETE FROM campaign_table")
    suspend fun deleteAll()
}