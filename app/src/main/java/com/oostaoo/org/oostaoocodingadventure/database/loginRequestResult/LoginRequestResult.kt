package com.oostaoo.org.oostaoocodingadventure.database.loginRequestResult

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oostaoo.org.oostaoocodingadventure.database.user.User

@Entity(tableName = "login_request_result_table")
class LoginRequestResult(@PrimaryKey val jwt: String,
                         @Embedded val user: User?)