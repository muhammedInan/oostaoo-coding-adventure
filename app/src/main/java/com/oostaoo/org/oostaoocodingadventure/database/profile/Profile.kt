package com.oostaoo.org.oostaoocodingadventure.database.profile

import androidx.room.*
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology

@Entity(tableName = "profile_table")
class Profile(@PrimaryKey @ColumnInfo(name = "idProfile") val id: Int,
              val name: String?,
              val created_at: String?,
              val updated_at: String?,
              @TypeConverters @ColumnInfo(name = "technologiesProfile") val technologies: List<Technology>?)