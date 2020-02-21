package com.oostaoo.org.oostaoocodingadventure.database.technology

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "technology_table")
class Technology(@PrimaryKey @ColumnInfo(name = "idTechnology") val id: Int,
                 val name: String?,
                 val description: String?,
                 val created_at: String?,
                 val updated_at: String?,
                 val profile: Int?)