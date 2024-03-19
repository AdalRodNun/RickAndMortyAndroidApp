package com.myapp.rickandmorty.data.repository

import com.myapp.rickandmorty.core.data.UserUUID
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.core.room.daos.CharacterDao
import com.myapp.rickandmorty.core.room.entities.CharacterEntity
import com.myapp.rickandmorty.data.model.CharacterModel
import com.myapp.rickandmorty.data.model.CharacterResponse
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.model.toDomain
import com.myapp.rickandmorty.service.RickAndMortyClient
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val api: RickAndMortyClient,
    private val characterDao: CharacterDao,
    private val userUUID: UserUUID,
) {
    suspend fun getAllCharacters(page: Int, characterName: String?): ApiResponse<CharacterResponse> {
        return api.getAllCharacters(page = page, characterName = characterName)
    }

    suspend fun getCharacterByID(characterID: Int): ApiResponse<CharacterModel> {
        return api.getCharacterByID(characterID = characterID)
    }

    suspend fun getAllCharactersByUserFromDatabase(): List<CharacterR> {
        val response: List<CharacterEntity> = characterDao.getAllCharactersByUserUUID(userUUID.getUserUUID())
        return response.map { it.toDomain() }
    }

    suspend fun addCharacterToDatabase(character: CharacterEntity) {
        characterDao.addCharacter(character)
    }

}