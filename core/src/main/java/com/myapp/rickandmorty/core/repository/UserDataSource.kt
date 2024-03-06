package com.myapp.rickandmorty.core.repository

import com.myapp.rickandmorty.core.data.User

interface UserDataSource {
    suspend fun add(user: User)

    suspend fun getByEmail(email: String): User?

}