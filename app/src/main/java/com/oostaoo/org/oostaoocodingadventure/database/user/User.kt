package com.oostaoo.org.oostaoocodingadventure.database.user

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oostaoo.org.oostaoocodingadventure.database.entreprise.Entreprise
import com.oostaoo.org.oostaoocodingadventure.database.role.Role

@Entity(tableName = "user_table")
class User(val username: String,
           @PrimaryKey @ColumnInfo(name = "idUser") val id: Int,
           val email: String?,
           val provider: String?,
           val confirmed: Boolean?,
           val blocked: Boolean?,
           @Embedded val role: Role?,
           val prenom: String?,
           val nom: String?,
           val pays: String?,
           val tel: String?,
           val langue: String?,
           val mobile: String?,
           val function: String?,
           val signature: String?,
           val adminId: Int?,
           val tests_available: Int?)