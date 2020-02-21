package com.oostaoo.org.oostaoocodingadventure.database.profile

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDao {

    @Query("SELECT * from profile_table ORDER BY idProfile ASC")
    fun getProfiles(): LiveData<List<Profile>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(profile: Profile)

    @Query("DELETE FROM profile_table")
    suspend fun deleteAll()
}