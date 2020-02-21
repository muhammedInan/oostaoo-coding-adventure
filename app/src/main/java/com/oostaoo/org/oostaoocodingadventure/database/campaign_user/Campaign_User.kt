package com.oostaoo.org.oostaoocodingadventure.database.campaign_user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campaign_user_table")
class Campaign_User(val username: String?,
                    @PrimaryKey @ColumnInfo(name = "idCampaign_User") val id: Int,
                    val email: String?,
                    val provider: String?,
                    val confirmed: Boolean?,
                    val blocked: Boolean?,
                    val role: Int?,
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