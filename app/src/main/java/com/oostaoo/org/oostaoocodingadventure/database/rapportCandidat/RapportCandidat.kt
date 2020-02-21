package com.oostaoo.org.oostaoocodingadventure.database.rapportCandidat

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.oostaoo.org.oostaoocodingadventure.database.rapport.Rapport

@Entity(tableName = "rapport_candidat_table")
class RapportCandidat(@PrimaryKey(autoGenerate = true) val idRapportCandidat: Int,
                      @TypeConverters(com.oostaoo.org.oostaoocodingadventure.database.TypeConverters::class) val rapport: List<Rapport>?)