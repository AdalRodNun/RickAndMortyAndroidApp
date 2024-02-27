package com.myapp.rickandmorty.services.repository


import com.myapp.rickandmorty.services.API
import com.myapp.rickandmorty.services.APIService
import com.myapp.rickandmorty.services.models.CharacterResponse
import retrofit2.Response

class ServiceRepository {

    private val service = API.getAPIRetrofit().create(APIService::class.java)

    suspend fun getCharacters(): Response<CharacterResponse> {
        return service.getCharacters()
    }

    suspend fun getCharactersByName(url: String): Response<CharacterResponse> {
        return service.getCharactersByName(url)
    }
}