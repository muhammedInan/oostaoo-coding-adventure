package com.oostaoo.org.oostaoocodingadventure.database.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
class Profile(@PrimaryKey @ColumnInfo(name = "idProfile") val id: Int,
              val name: String?,
              val created_at: String?,
              val updated_at: String?)