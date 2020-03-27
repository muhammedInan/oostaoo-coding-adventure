package com.oostaoo.org.oostaoocodingadventure.database.technology

import androidx.room.*
import com.oostaoo.org.oostaoocodingadventure.database.questionCampaign.QuestionCampaign

@Entity(tableName = "technology_table")
class Technology(@PrimaryKey @ColumnInfo(name = "idTechnology") val id: Int,
                 val name: String?,
                 val description: String?,
                 val created_at: String?,
                 val updated_at: String?,
                 @TypeConverters val questions: List<QuestionCampaign>?)