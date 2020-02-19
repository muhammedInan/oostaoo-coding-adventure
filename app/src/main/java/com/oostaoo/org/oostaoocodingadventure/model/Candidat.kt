package com.oostaoo.org.oostaoocodingadventure.model

import org.json.JSONArray
import org.json.JSONObject

class Candidat(val id: Int, val Nom: String, val email: String, val created_at: String,
               val updated_at: String, val campaign: Int, val invitation_date: String,
               val token: String, val duree: Int, val test_terminer: String,
               val test_ouvert: String, val index_question: Int, val test_pause: Int,
               val date_pause: String, val raport_candidat: RapportCandidat/*, val points_candidat: JSONArray*/)