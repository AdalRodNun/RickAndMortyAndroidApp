package com.myapp.rickandmorty.data.repository

import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.core.room.daos.UserDao
import com.myapp.rickandmorty.core.room.entities.UserEntity.Companion.toEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun checkIfUserExist(email: String) : Boolean {
        return userDao.checkIfUserExist(email)
    }

    suspend fun addUser(user: User) {
        userDao.addUser(user.toEntity())
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.fromEntity()
    }
}