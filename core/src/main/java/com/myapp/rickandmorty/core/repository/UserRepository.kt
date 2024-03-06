package com.myapp.rickandmorty.core.repository

import com.myapp.rickandmorty.core.data.User

class UserRepository(private val dataSource: UserDataSource) {
    suspend fun addUser(user: User) = dataSource.add(user)

    suspend fun getUserByEmail(email: String) = dataSource.getByEmail(email)

}