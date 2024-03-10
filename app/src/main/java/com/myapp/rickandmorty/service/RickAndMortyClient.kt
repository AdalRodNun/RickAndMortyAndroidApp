package com.myapp.rickandmorty.service

import com.myapp.rickandmorty.core.retrofit.ApiHandler
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.data.model.CharacterResponse
import javax.inject.Inject

class RickAndMortyClient @Inject constructor(
    private val api: RickAndMortyService,
    private val apiHandler: ApiHandler
) {
    suspend fun getCharacters(): ApiResponse<CharacterResponse> {
        return apiHandler.handleApi { api.getCharacters() }
    }

    suspend fun getCharactersByName(name: String): ApiResponse<CharacterResponse> {
        return apiHandler.handleApi { api.getCharactersByName(name) }
    }
}