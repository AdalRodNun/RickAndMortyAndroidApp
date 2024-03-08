package com.myapp.rickandmorty.data.repository

import com.myapp.rickandmorty.CharacterService
import com.myapp.rickandmorty.core.room.daos.CharacterDao
import com.myapp.rickandmorty.core.room.entities.CharacterEntity
import com.myapp.rickandmorty.data.model.CharacterModel
import com.myapp.rickandmorty.di.UserUUID
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.model.toDomain
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val api: CharacterService,
    private val characterDao: CharacterDao,
    private val userUUID: UserUUID
) {
    suspend fun getCharacters(): List<CharacterR> {
        val response: List<CharacterModel> = api.getCharacters()
        return response.map { it.toDomain() }
    }

    suspend fun getCharactersByName(name: String): List<CharacterR> {
        val response: List<CharacterModel> = api.getCharactersByName(name)
        return response.map { it.toDomain() }
    }

    suspend fun getAllCharactersByUserFromDatabase(): List<CharacterR> {
        val response: List<CharacterEntity> = characterDao.getAllCharactersByUserUUID(userUUID.getUserUUID())
        return response.map { it.toDomain() }
    }

    suspend fun addCharacterToDatabase(character: CharacterEntity) {
        characterDao.addCharacter(character)
    }
}