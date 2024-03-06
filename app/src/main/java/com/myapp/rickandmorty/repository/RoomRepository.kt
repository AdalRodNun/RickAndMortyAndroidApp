package com.myapp.rickandmorty.repository

import com.myapp.rickandmorty.core.App.Companion.room
import com.myapp.rickandmorty.core.App.Companion.userUUID
import com.myapp.rickandmorty.core.data.Person
import com.myapp.rickandmorty.core.framework.room.models.PersonEntity.Companion.toEntity

class RoomRepository {
    //Person
    suspend fun addPerson(person: Person) {
        room.personDao().addPerson(person.toEntity())
    }

    suspend fun getAllPersonsByUserUUID(): List<Person> {
        return room.personDao().getAllPersonsByUserUUID(userUUID).map { it.fromEntity() }
    }
}
