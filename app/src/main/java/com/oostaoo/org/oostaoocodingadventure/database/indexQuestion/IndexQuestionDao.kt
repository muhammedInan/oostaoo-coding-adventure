package com.oostaoo.org.oostaoocodingadventure.database.indexQuestion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IndexQuestionDao {

    @Query("SELECT * from index_question_table ORDER BY idIndexQuestion ASC")
    fun getindexQuestions(): LiveData<List<IndexQuestion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(indexQuestion: IndexQuestion)

    @Query("DELETE FROM index_question_table")
    suspend fun deleteAll()
}