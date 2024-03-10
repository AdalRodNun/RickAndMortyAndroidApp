package com.myapp.rickandmorty.data.repository

import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.service.RickAndMortyClient
import com.myapp.rickandmorty.core.room.daos.CharacterDao
import com.myapp.rickandmorty.core.room.entities.CharacterEntity
import com.myapp.rickandmorty.data.model.CharacterResponse
import com.myapp.rickandmorty.core.data.UserUUID
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.model.toDomain
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val api: RickAndMortyClient,
    private val characterDao: CharacterDao,
    private val userUUID: UserUUID
) {
    suspend fun getCharacters(): ApiResponse<CharacterResponse> {
        return api.getCharacters()
    }

    suspend fun getCharactersByName(name: String): ApiResponse<CharacterResponse> {
        return api.getCharactersByName(name)
    }

    suspend fun getAllCharactersByUserFromDatabase(): List<CharacterR> {
        val response: List<CharacterEntity> = characterDao.getAllCharactersByUserUUID(userUUID.getUserUUID())
        return response.map { it.toDomain() }
    }

    suspend fun addCharacterToDatabase(character: CharacterEntity) {
        characterDao.addCharacter(character)
    }
}