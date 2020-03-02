package com.oostaoo.org.oostaoocodingadventure.database.role

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoleDao {

    @Query("SELECT * from role_table ORDER BY idRole ASC")
    fun getRoles(): LiveData<List<Role>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(role: Role)

    @Query("DELETE FROM role_table")
    suspend fun deleteAll()
}