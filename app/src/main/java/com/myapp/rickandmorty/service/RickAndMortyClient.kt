package com.myapp.rickandmorty.service

import com.myapp.rickandmorty.core.retrofit.ApiHandler
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.data.model.CharacterModel
import com.myapp.rickandmorty.data.model.CharacterResponse
import javax.inject.Inject

class RickAndMortyClient @Inject constructor(
    private val api: RickAndMortyService,
    private val apiHandler: ApiHandler
) {
    suspend fun getAllCharacters(page: Int, characterName: String?): ApiResponse<CharacterResponse> {
        return apiHandler.handleApi { api.getAllCharacters(page = page, name = characterName ?: "") }
    }

    suspend fun getCharacterByID(characterID: Int): ApiResponse<CharacterModel> {
        return apiHandler.handleApi { api.getCharacterByID(id = characterID) }
    }
}