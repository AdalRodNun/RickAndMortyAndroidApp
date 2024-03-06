package com.myapp.rickandmorty.framework

import com.myapp.rickandmorty.core.App.Companion.room
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.core.framework.room.models.UserEntity.Companion.toEntity
import com.myapp.rickandmorty.core.repository.UserDataSource

class RoomUserDataSource : UserDataSource {
    private val userDao = room.userDao()

    override suspend fun add(user: User) = userDao.addUser(user.toEntity())

    override suspend fun getByEmail(email: String): User? =
        userDao.getUserByEmail(email)?.fromEntity()
}