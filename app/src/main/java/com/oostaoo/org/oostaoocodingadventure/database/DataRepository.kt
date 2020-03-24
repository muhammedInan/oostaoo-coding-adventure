package com.oostaoo.org.oostaoocodingadventure.database

import androidx.lifecycle.LiveData
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.entreprise.Entreprise
import com.oostaoo.org.oostaoocodingadventure.database.profile.Profile
import com.oostaoo.org.oostaoocodingadventure.database.question.Question
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology
import com.oostaoo.org.oostaoocodingadventure.database.user.User

class DataRepository() {

    private lateinit var mDatabase: AppDatabase
    private var sInstance: DataRepository? = null

    constructor(database: AppDatabase) : this() {
        mDatabase = database
    }

    fun getInstance(database: AppDatabase?): DataRepository? {
        if (sInstance == null) {
            synchronized(DataRepository::class.java) {
                if (sInstance == null) {
                    sInstance = DataRepository(database!!)
                }
            }
        }
        return sInstance
    }

    //Campaigns

    fun getCampaigns(): LiveData<List<Campaign>> {
        return mDatabase.campaignDao().getCampaigns()
    }

    fun getCampaign(idCampaign: Int): LiveData<Campaign> {
        return mDatabase.campaignDao().getCampaign(idCampaign)
    }

    suspend fun insertCampaign(campaign: Campaign) {
        mDatabase.campaignDao().insert(campaign)
    }

    suspend fun deleteAllCampaigns() {
        mDatabase.campaignDao().deleteAll()
    }

    //Profiles

    fun getProfiles(): LiveData<List<Profile>> {
        return mDatabase.profileDao().getProfiles()
    }

    suspend fun insertProfile(profile: Profile) {
        mDatabase.profileDao().insert(profile)
    }

    suspend fun deleteAllProfiles() {
        mDatabase.profileDao().deleteAll()
    }

    //Technologies

    fun getTechnologies(): LiveData<List<Technology>> {
        return mDatabase.technologyDao().getTechnologies()
    }

    suspend fun insertTechnology(technology: Technology) {
        mDatabase.technologyDao().insert(technology)
    }

    suspend fun deleteAllTechnologies() {
        mDatabase.technologyDao().deleteAll()
    }

    //Questions

    suspend fun insertQuestion(question: Question) {
        mDatabase.questionDao().insert(question)
    }

    //User

    suspend fun insertUser(user: User) {
        mDatabase.userDao().insert(user)
    }

    fun getUser(idUser: Int): LiveData<User> {
        return mDatabase.userDao().getUser(idUser)
    }

    fun getUsers(adminId: Int) : LiveData<List<User>> {
        return mDatabase.userDao().getUsers(adminId)
    }

    //Entreprise

    suspend fun insertEntreprise(entreprise: Entreprise) {
        mDatabase.entrepriseDao().insert(entreprise)
    }

    fun getEntreprise(idEntreprise: Int): LiveData<Entreprise> {
        return mDatabase.entrepriseDao().getEntreprise(idEntreprise)
    }
}