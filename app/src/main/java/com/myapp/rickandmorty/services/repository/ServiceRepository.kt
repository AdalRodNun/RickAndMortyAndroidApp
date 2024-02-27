package com.myapp.rickandmorty.services.repository

import com.myapp.rickandmorty.services.APIService.Companion.service
import com.myapp.rickandmorty.services.models.CharacterResponse
import retrofit2.Response

class ServiceRepository {

    suspend fun getCharacters(): Response<CharacterResponse> {
        return service.getCharacters()
    }

    suspend fun getCharactersByName(url: String): Response<CharacterResponse> {
        return service.getCharactersByName(url)
    }
}