package com.oostaoo.org.oostaoocodingadventure.utils

fun isEmailValid(email: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun secondsToString(pTime: Int): String? {
    val hours = (pTime / 60 / 60)
    val minutes = (pTime / 60) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, pTime % 60)
}