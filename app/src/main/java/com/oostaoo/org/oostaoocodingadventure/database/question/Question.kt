package com.oostaoo.org.oostaoocodingadventure.database.question

import androidx.room.*
import com.oostaoo.org.oostaoocodingadventure.database.questionCampaign.QuestionCampaign
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology

@Entity(tableName = "question_table")
class Question(@PrimaryKey @ColumnInfo(name = "idQuestion") val id: Int,
               @ColumnInfo(name = "nameQuestion") val name: String?,
               @ColumnInfo(name = "question_created_at") val created_at: String?,
               @ColumnInfo(name = "question_updated_at") val updated_at: String?,
               val points: Int?,
               val level: String?,
               val type: String?,
               val content: String?,
               val time: Int?,
               val theme: String?,
               @Embedded val technologies: Technology?,
               val answer_value: String?,
               val question: Int?,
               val numero: Int?,
               @TypeConverters val answers: List<String>?,
               @TypeConverters val campaigns: List<QuestionCampaign>?)