package com.oostaoo.org.oostaoocodingadventure.database.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology

@Entity(tableName = "question_table")
class Question(@PrimaryKey @ColumnInfo(name = "idQuestion") val id: Int,
               val name: String?,
               val created_at: String?,
               val updated_at: String?,
               val points: Int?,
               val level: String?,
               val type: String?,
               val content: String?,
               val time: Int?,
               val theme: String?,
               val technologies: Int?,
               val answer_value: String?,
               val question: Int?,
               val numero: Int?)