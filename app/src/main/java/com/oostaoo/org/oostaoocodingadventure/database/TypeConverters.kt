package com.oostaoo.org.oostaoocodingadventure.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.candidat.Candidat
import com.oostaoo.org.oostaoocodingadventure.database.candidat.PointCandidats
import com.oostaoo.org.oostaoocodingadventure.database.questionCampaign.QuestionCampaign
import com.oostaoo.org.oostaoocodingadventure.database.rapport.Rapport
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology

class TypeConverters {

    @TypeConverter
    fun fromCampaignList(value: List<Campaign>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Campaign>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCampaignList(value: String): List<Campaign>? {
        val gson = Gson()
        val type = object : TypeToken<List<Campaign>?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromTechnologyList(value: List<Technology>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Technology>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTechnologyList(value: String): List<Technology>? {
        val gson = Gson()
        val type = object : TypeToken<List<Technology>?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCandidatList(value: List<Candidat>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Candidat>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCandidatList(value: String): List<Candidat>? {
        val gson = Gson()
        val type = object : TypeToken<List<Candidat>?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromQuestionCampaignList(value: List<QuestionCampaign>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<QuestionCampaign>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toQuestionCampaignList(value: String): List<QuestionCampaign>? {
        val gson = Gson()
        val type = object : TypeToken<List<QuestionCampaign>?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromRapportList(value: List<Rapport>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Rapport>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toRapportList(value: String): List<Rapport>? {
        val gson = Gson()
        val type = object : TypeToken<List<Rapport>?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromPointCandidatList(value: List<PointCandidats>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<PointCandidats>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPointCandidatList(value: String): List<PointCandidats>? {
        val gson = Gson()
        val type = object : TypeToken<List<PointCandidats>?>() {}.type
        return gson.fromJson(value, type)
    }
}