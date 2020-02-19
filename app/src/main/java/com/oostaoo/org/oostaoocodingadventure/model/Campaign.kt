package com.oostaoo.org.oostaoocodingadventure.model

class Campaign(val id: Int, val Name: String, val level: String, val langs: String,
               val copy_paste: Boolean, val sent_report: Boolean, val created_at: String,
               val updated_at: String, val expiration_date: String, val stopwatch: String,
               val email_title: String, val email_content: String, val profile: Profile,
               val NbCandidatFinish: Int, val user: Campaign_User, val archive: Boolean, val pin: Boolean,
               val technologies: List<Technology>, val candidats: List<Candidat>,
               val questions: List<Question>)