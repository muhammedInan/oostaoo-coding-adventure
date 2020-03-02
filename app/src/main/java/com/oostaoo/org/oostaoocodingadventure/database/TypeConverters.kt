package com.oostaoo.org.oostaoocodingadventure.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oostaoo.org.oostaoocodingadventure.database.candidat.Candidat
import com.oostaoo.org.oostaoocodingadventure.database.question.Question
import com.oostaoo.org.oostaoocodingadventure.database.rapport.Rapport
import com.oostaoo.org.oostaoocodingadventure.database.technology.Technology


class TypeConverters {

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
    fun fromCandidatList(value: List<Candidat>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Candidat>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCandidatList(value: String): List<Candidat> {
        val gson = Gson()
        val type = object : TypeToken<List<Candidat>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromQuestionList(value: List<Question>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Question>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toQuestionList(value: String): List<Question> {
        val gson = Gson()
        val type = object : TypeToken<List<Question>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromRapportList(value: List<Rapport>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Rapport>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toRapportList(value: String): List<Rapport> {
        val gson = Gson()
        val type = object : TypeToken<List<Rapport>>() {}.type
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
}