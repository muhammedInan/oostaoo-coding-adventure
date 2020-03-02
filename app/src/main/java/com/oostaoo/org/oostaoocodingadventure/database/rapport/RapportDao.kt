package com.oostaoo.org.oostaoocodingadventure.database.rapport

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RapportDao {

    @Query("SELECT * from rapport_table ORDER BY timeRep ASC")
    fun getRapports(): LiveData<List<Rapport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rapport: Rapport)

    @Query("DELETE FROM rapport_table")
    suspend fun deleteAll()
}