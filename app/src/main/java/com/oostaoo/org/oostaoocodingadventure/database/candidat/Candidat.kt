package com.oostaoo.org.oostaoocodingadventure.database.candidat

import androidx.room.*
import com.oostaoo.org.oostaoocodingadventure.database.rapportCandidat.RapportCandidat

@Entity(tableName = "candidat_table")
class Candidat(@PrimaryKey @ColumnInfo(name = "idCandidat") val id: Int,
               val Nom: String?,
               val email: String?,
               val created_at: String?,
               val updated_at: String?,
               val campaign: Int?,
               val invitation_date: String?,
               val token: String?,
               val duree: Int?,
               val test_terminer: String?,
               val test_ouvert: String?,
               val index_question: Int?,
               val test_pause: Int?,
               val date_pause: String?,
               @Embedded val raport_candidat: RapportCandidat?,
               @TypeConverters val points_candidat: List<PointCandidats>)

class PointCandidats(
    val allPointsTechnos: List<Score>?,
    val allPointsCandidat: List<Score>?,
    val getpourcentByCandidat: List<Score>?,
    val totalPointsCandidat: Int?,
    val totalPointsCampaign: Int?,
    val PourcentTest: Int?)

class Score(val technologies: String, val point: Int)