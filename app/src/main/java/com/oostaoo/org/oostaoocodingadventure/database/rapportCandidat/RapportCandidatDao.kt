package com.oostaoo.org.oostaoocodingadventure.database.rapportCandidat

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RapportCandidatDao {

    @Query("SELECT * from rapport_candidat_table ORDER BY idRapportCandidat ASC")
    fun getRapportCandidats(): LiveData<List<RapportCandidat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rapportCandidat: RapportCandidat)

    @Query("DELETE FROM rapport_candidat_table")
    suspend fun deleteAll()
}