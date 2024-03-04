package com.myapp.rickandmorty.repository

import com.myapp.rickandmorty.core.App.Companion.room
import com.myapp.rickandmorty.core.App.Companion.userUUID
import com.myapp.rickandmorty.core.data.Person
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.core.room.models.PersonEntity.Companion.toEntity
import com.myapp.rickandmorty.core.room.models.UserEntity.Companion.toEntity

class RoomRepository {
    //User
    suspend fun addUser(user: User) {
        room.userDao().addUser(user.toEntity())
    }

    suspend fun findUserByEmail(email: String): User? {
        return room.userDao().findUserByEmail(email)?.fromEntity()
    }

    //Person
    suspend fun addPerson(person: Person) {
        room.personDao().addPerson(person.toEntity())
    }

    suspend fun getAllPersonsByUserUUID(): List<Person> {
        return room.personDao().getAllPersonsByUserUUID(userUUID).map { it.fromEntity() }
    }
}
