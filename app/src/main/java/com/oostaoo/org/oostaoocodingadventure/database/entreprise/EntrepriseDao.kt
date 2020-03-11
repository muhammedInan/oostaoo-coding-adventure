package com.oostaoo.org.oostaoocodingadventure.database.entreprise

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EntrepriseDao {

    @Query("SELECT * from entreprise_table ORDER BY idEntreprise ASC")
    fun getEntreprises(): LiveData<List<Entreprise>>

    @Query("SELECT * from entreprise_table WHERE idEntreprise = :id")
    fun getEntreprise(id: Int): LiveData<Entreprise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entereprise: Entreprise)

    @Query("DELETE FROM entreprise_table")
    suspend fun deleteAll()
}