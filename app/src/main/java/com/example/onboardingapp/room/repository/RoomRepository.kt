package com.example.onboardingapp.room.repository

import com.example.onboardingapp.App
import com.example.onboardingapp.room.models.Person
import com.example.onboardingapp.room.models.User

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
