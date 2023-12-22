package com.example.onboardingapp.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onboardingapp.room.models.Person

@Dao
interface PersonDao {
    @Query("SELECT * from Person")
    suspend fun getAllPersons(): List<Person>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: Person)
}