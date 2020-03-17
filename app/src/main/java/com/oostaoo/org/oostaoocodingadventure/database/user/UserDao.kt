package com.oostaoo.org.oostaoocodingadventure.database.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * from user_table ORDER BY idUser ASC")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * from user_table WHERE adminId = :adminId ORDER BY idUser ASC")
    fun getUsers(adminId: Int): LiveData<List<User>>

    @Query("SELECT * from user_table WHERE idUser = :id")
    fun getUser(id: Int): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}