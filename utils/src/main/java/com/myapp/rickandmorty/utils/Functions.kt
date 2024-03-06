package com.myapp.rickandmorty.utils

object Functions {

    fun isValidEmail(email: String): Boolean {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}