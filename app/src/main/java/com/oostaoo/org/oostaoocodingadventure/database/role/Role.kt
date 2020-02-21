package com.oostaoo.org.oostaoocodingadventure.database.role

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "role_table")
class Role(@PrimaryKey @ColumnInfo(name = "idRole") val id: Int,
           val name: String?,
           val description: String?,
           val type: String?)