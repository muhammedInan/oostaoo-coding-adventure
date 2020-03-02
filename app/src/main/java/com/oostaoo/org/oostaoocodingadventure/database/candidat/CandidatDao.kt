package com.oostaoo.org.oostaoocodingadventure.database.candidat

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CandidatDao {

    @Query("SELECT * from candidat_table ORDER BY idCandidat ASC")
    fun getCandidats(): LiveData<List<Candidat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(candidat: Candidat)

    @Query("DELETE FROM candidat_table")
    suspend fun deleteAll()
}