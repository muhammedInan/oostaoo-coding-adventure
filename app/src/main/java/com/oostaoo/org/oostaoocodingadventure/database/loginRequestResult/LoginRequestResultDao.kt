package com.oostaoo.org.oostaoocodingadventure.database.loginRequestResult

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoginRequestResultDao {

    @Query("SELECT * from login_request_result_table ORDER BY jwt ASC")
    fun getLoginRequestResults(): LiveData<List<LoginRequestResult>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(loginRequestResult: LoginRequestResult)

    @Query("DELETE FROM login_request_result_table")
    suspend fun deleteAll()
}