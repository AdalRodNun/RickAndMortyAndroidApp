package com.myapp.rickandmorty.core.framework.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.myapp.rickandmorty.core.framework.room.models.PersonEntity
import java.util.UUID

@Dao
interface PersonDao {

    @Insert(onConflict = IGNORE)
    suspend fun addPerson(person: PersonEntity)

    @Query("SELECT * FROM person WHERE userUUID == :userUUID")
    suspend fun getAllPersonsByUserUUID(userUUID: UUID): List<PersonEntity>

}