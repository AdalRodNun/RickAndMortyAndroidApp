package com.example.rickandmorty.room.repository

import com.example.rickandmorty.App
import com.example.rickandmorty.room.models.Person
import com.example.rickandmorty.room.models.User

class RoomRepository {
    //User
    suspend fun addUser(user: User) {
        App.room.userDao().addUser(user)
    }

    suspend fun findUserByEmail(email: String): User? {
        return App.room.userDao().findUserByEmail(email)
    }

    //Person
    suspend fun addPerson(person: Person) {
        App.room.personDao().addPerson(person)
    }

    suspend fun getAllPersons(): List<Person> {
        return App.room.personDao().getAllPersons()
    }
}
