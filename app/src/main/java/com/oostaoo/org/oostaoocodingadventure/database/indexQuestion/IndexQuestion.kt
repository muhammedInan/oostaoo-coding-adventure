package com.oostaoo.org.oostaoocodingadventure.database.indexQuestion

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "index_question_table")
class IndexQuestion(val points: Int,
                    val level: String,
                    val question: Int,
                    val theme: String,
                    val created_at: String,
                    val technologies: Int,
                    val name: String,
                    val time: Int,
                    val answer_value: String,
                    val updated_at: String,
                    val content: String,
                    val type: String,
                    @PrimaryKey @ColumnInfo(name = "idIndexQuestion") val id: Int,
                    val numero: Int)