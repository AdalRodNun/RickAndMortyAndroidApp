package com.myapp.rickandmorty.utils

import android.util.Patterns

object Functions {

    fun isValidEmail(email: String): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}