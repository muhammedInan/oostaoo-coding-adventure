package com.oostaoo.org.oostaoocodingadventure.database.user_login_request_result

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oostaoo.org.oostaoocodingadventure.database.role.Role

@Entity(tableName = "user__login_request_result_table")
class User_LoginRequestResult(val username: String,
           @PrimaryKey @ColumnInfo(name = "idUser_LoginRequestResult") val id: Int,
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
           val entreprise: Int?,
           val adminId: Int?,
           val tests_available: Int?)