package com.oostaoo.org.oostaoocodingadventure.database.questionCampaign

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign

@Entity(tableName = "question_campaign_table")
class QuestionCampaign(@PrimaryKey @ColumnInfo(name = "idQuestionCampaign") val id: Int,
                       @ColumnInfo(name = "nameQuestionCampaign") val name: String?,
                       @ColumnInfo(name = "question_campaign_created_at") val created_at: String?,
                       @ColumnInfo(name = "question_campaign_updated_at") val updated_at: String?,
                       val points: Int?,
                       val level: String?,
                       val type: String?,
                       val content: String?,
                       val time: Int?,
                       val theme: String?,
                       val technologies: Int?,
                       val answer_value: String?,
                       val question: Int?,
                       val numero: Int?,
                       @TypeConverters val answers: List<String>?,
                       @TypeConverters val campaigns: List<Campaign>?)