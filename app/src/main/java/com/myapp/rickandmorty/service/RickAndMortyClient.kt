package com.myapp.rickandmorty.service

import com.myapp.rickandmorty.core.retrofit.ApiHandler
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.data.model.CharacterResponse
import javax.inject.Inject

class RickAndMortyClient @Inject constructor(
    private val api: RickAndMortyService,
    private val apiHandler: ApiHandler
) {
    suspend fun getAllCharacters(page: Int): ApiResponse<CharacterResponse> {
        return apiHandler.handleApi { api.getAllCharacters(page) }
    }

    suspend fun getCharactersByName(name: String): ApiResponse<CharacterResponse> {
        return apiHandler.handleApi { api.getCharactersByName(name) }
    }
}