package com.myapp.rickandmorty.di

import java.util.UUID

class UserUUID {
    private lateinit var userUUID: UUID

    fun setUserUUID(userID: UUID) {
        userUUID = userID
    }

    fun getUserUUID(): UUID {
        return userUUID
    }
}