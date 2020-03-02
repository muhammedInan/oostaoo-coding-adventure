package com.oostaoo.org.oostaoocodingadventure.database.technology

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TechnologyDao {

    @Query("SELECT * from technology_table ORDER BY idTechnology ASC")
    fun getTechnologies(): LiveData<List<Technology>>

    @Query("SELECT * from technology_table WHERE name = :name")
    fun getTechnology(name: String): LiveData<Technology>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(technology: Technology)

    @Query("DELETE FROM technology_table")
    suspend fun deleteAll()
}