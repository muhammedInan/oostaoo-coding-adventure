package com.oostaoo.org.oostaoocodingadventure.database.questionCampaign

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionCampaignDao {

    @Query("SELECT * from question_campaign_table ORDER BY idQuestionCampaign ASC")
    fun getQuestions(): LiveData<List<QuestionCampaign>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionCampaign)

    @Query("DELETE FROM question_campaign_table")
    suspend fun deleteAll()
}