package com.oostaoo.org.oostaoocodingadventure.database.entreprise

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entreprise_table")
class Entreprise(@PrimaryKey @ColumnInfo(name = "idEntreprise") val id: Int,
                 val utilisateursadmin: Int?,
                 @ColumnInfo(name = "nomEntreprise") val nom: String?,
                 @ColumnInfo(name = "emailEntreprise") val email: String?,
                 @ColumnInfo(name = "telEntreprise") val tel: String?,
                 val nb_employe: String?,
                 val nb_dev: String?,
                 val lien_video: String,
                 val url_site: String?,
                 val teaser: String?,
                 val linkedin: String?,
                 val facebook: String?,
                 val twitter: String?,
                 val created_at: String?,
                 val updated_at: String?,
                 val lang: String?,
                 val industrie: String?)