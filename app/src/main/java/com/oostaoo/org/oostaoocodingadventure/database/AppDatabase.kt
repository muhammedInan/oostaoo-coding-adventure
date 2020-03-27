package com.oostaoo.org.oostaoocodingadventure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.campaign.CampaignDao
import com.oostaoo.org.oostaoocodingadventure.database.campaign_question.Campaign_Question
import com.oostaoo.org.oostaoocodingadventure.database.campaign_user.Campaign_User
import com.oostaoo.org.oostaoocodingadventure.database.campaign_user.Campaign_UserDao
import com.oostaoo.org.oostaoocodingadventure.database.candidat.Candidat
import com.oostaoo.org.oostaoocodingadventure.database.candidat.CandidatDao
import com.oostaoo.org.oostaoocodingadventure.database.entreprise.Entreprise
import com.oostaoo.org.oostaoocodingadventure.database.entreprise.EntrepriseDao
import com.oostaoo.org.oostaoocodingadventure.database.indexQuestion.IndexQuestion
import com.oostaoo.org.oostaoocodingadventure.database.indexQuestion.IndexQuestionDao
import com.oostaoo.org.oostaoocodingadventure.database.loginRequestResult.LoginRequestResult
import com.oostaoo.org.oostaoocodingadventure.database.loginRequestResult.LoginRequestResultDao
import com.oostaoo.org.oostaoocodingadventure.database.profile.Profile
import com.oostaoo.org.oostaoocodingadventure.database.profile.ProfileDao
import com.oostaoo.org.oostaoocodingadventure.database.question.Question
import com.oostaoo.org.oostaoocodingadventure.database.question.QuestionDao
import com.oostaoo.org.oostaoocodingadventure.database.questionCampaign.QuestionCampaign
import com.oostaoo.org.oostaoocodingadventure.database.questionCampaign.QuestionCampaignDao
import com.oostaoo.org.oostaoocodingadventure.database.rapport.Rapport
import com.oostaoo.org.oostaoocodingadventure.database.rapport.RapportDao
import com.oostaoo.org.oostaoocodingadventure.database.rapportCandidat.RapportCandidat
import com.oostaoo.org.oostaoocodingadventure.database.rapportCandidat.RapportCandidatDao
import com.oostaoo.org.oostaoocodingadventure.database.role.Role
import com.oostaoo.org.oostaoocodingadventure.database.role.RoleDao
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology
import com.oostaoo.org.oostaoocodingadventure.database.technology.TechnologyDao
import com.oostaoo.org.oostaoocodingadventure.database.user.User
import com.oostaoo.org.oostaoocodingadventure.database.user.UserDao

@Database(entities = arrayOf(Campaign::class, Campaign_Question::class, Campaign_User::class, Candidat::class,
    Entreprise::class, IndexQuestion::class, LoginRequestResult::class, Profile::class,
    Question::class, QuestionCampaign::class, Rapport::class, RapportCandidat::class, Role::class, Technology::class,
    User::class), version = 1)
@TypeConverters(com.oostaoo.org.oostaoocodingadventure.database.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun campaignDao(): CampaignDao
    abstract fun campaignUserDao(): Campaign_UserDao
    abstract fun candidatDao(): CandidatDao
    abstract fun entrepriseDao(): EntrepriseDao
    abstract fun indexQuestionDao(): IndexQuestionDao
    abstract fun loginRequestResultDao(): LoginRequestResultDao
    abstract fun profileDao(): ProfileDao
    abstract fun questionDao(): QuestionDao
    abstract fun questionCampaignDao(): QuestionCampaignDao
    abstract fun rapportDao(): RapportDao
    abstract fun rapportCandidatDao(): RapportCandidatDao
    abstract fun roleDao(): RoleDao
    abstract fun technologyDao(): TechnologyDao
    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}