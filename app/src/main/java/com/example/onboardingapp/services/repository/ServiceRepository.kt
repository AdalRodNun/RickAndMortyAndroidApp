package com.example.onboardingapp.services.repository

import com.example.onboardingapp.services.API
import com.example.onboardingapp.services.APIService
import com.example.onboardingapp.services.models.CharacterResponse
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