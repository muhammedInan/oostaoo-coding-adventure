package com.oostaoo.org.oostaoocodingadventure.database.question

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {

    @Query("SELECT * from question_table ORDER BY idQuestion ASC")
    fun getQuestions(): LiveData<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: Question)

    @Query("DELETE FROM question_table")
    suspend fun deleteAll()
}