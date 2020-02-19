package com.oostaoo.org.oostaoocodingadventure.model

class User(val username: String, val id: Int, val email: String, val provider: String,
           val confirmed: Boolean, val blocked: Boolean, val role: Role, val prenom: String,
           val nom: String, val pays: String, val tel: String, val langue: String,
           val mobile: String, val function: String, val signature: String, val entreprise: Int,
           val adminId: Int, val tests_available: Int)