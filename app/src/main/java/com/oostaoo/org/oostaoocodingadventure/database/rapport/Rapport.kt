package com.oostaoo.org.oostaoocodingadventure.database.rapport

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.oostaoo.org.oostaoocodingadventure.database.indexQuestion.IndexQuestion

@Entity(tableName = "rapport_table")
class Rapport(@Embedded @PrimaryKey val index_question: IndexQuestion,
              @TypeConverters(com.oostaoo.org.oostaoocodingadventure.database.TypeConverters::class) val array_rep_candidat: List<String>?,
              val timeRep: Int?)