package com.myapp.rickandmorty.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.rickandmorty.room.models.Person

@Dao
interface PersonDao {
    @Query("SELECT * from Person")
    suspend fun getAllPersons(): List<Person>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: Person)
}