package com.myapp.rickandmorty.core.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.myapp.rickandmorty.core.room.entities.CharacterEntity
import java.util.UUID

@Dao
interface CharacterDao {

    @Insert(onConflict = IGNORE)
    suspend fun addCharacter(person: CharacterEntity)

    @Query("SELECT * FROM characters WHERE userUUID == :userUUID")
    suspend fun getAllCharactersByUserUUID(userUUID: UUID): List<CharacterEntity>

}