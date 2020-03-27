package com.oostaoo.org.oostaoocodingadventure.database.campaign_question

import androidx.room.*
import com.oostaoo.org.oostaoocodingadventure.database.campaign_user.Campaign_User
import com.oostaoo.org.oostaoocodingadventure.database.candidat.Candidat
import com.oostaoo.org.oostaoocodingadventure.database.profile.Profile
import com.oostaoo.org.oostaoocodingadventure.database.questionCampaign.QuestionCampaign
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology

@Entity(tableName = "campaign_question_table")
class Campaign_Question(@PrimaryKey @ColumnInfo(name = "idCampaign") val id: Int,
                        @ColumnInfo(name = "Campaign_name") val Name: String?,
                        @ColumnInfo(name = "level") val level: String?,
                        @ColumnInfo(name = "langs") val langs: String?,
                        @ColumnInfo(name = "copy_paste") val copy_paste: Boolean?,
                        @ColumnInfo(name = "sent_report") val sent_report: Boolean?,
                        @ColumnInfo(name = "campaign_created_at") val created_at: String?,
                        @ColumnInfo(name = "campaign_updated_at") val updated_at: String?,
                        @ColumnInfo(name = "expiration_date") val expiration_date: String?,
                        @ColumnInfo(name = "stopwatch") val stopwatch: String?,
                        @ColumnInfo(name = "email_title") val email_title: String?,
                        @ColumnInfo(name = "email_content") val email_content: String?,
                        val profile: Int?,
                        @ColumnInfo(name = "NbCandidatFinish") val NbCandidatFinish: Int?,
                        @Embedded val user: Campaign_User?,
                        @ColumnInfo(name = "archive") val archive: Boolean?,
                        @ColumnInfo(name = "pin") val pin: Boolean?)