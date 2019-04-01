package com.oostaoo.org.oostaoocodingadventure.utils

fun isEmailValid(email: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}