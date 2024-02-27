package com.myapp.rickandmorty.repository

import com.myapp.rickandmorty.core.App.Companion.room
import com.myapp.rickandmorty.core.room.models.Person
import com.myapp.rickandmorty.core.room.models.User

class RoomRepository {
    //User
    suspend fun addUser(user: User) {
        room.userDao().addUser(user)
    }

    suspend fun findUserByEmail(email: String): User? {
        return room.userDao().findUserByEmail(email)
    }

    //Person
    suspend fun addPerson(person: Person) {
        room.personDao().addPerson(person)
    }

    suspend fun getAllPersons(): List<Person> {
        return room.personDao().getAllPersons()
    }
}
