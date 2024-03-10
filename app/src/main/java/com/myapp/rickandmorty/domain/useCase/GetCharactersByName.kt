package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.data.model.CharacterResponse
import com.myapp.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersByName @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(name: String): ApiResponse<CharacterResponse> {
        return repository.getCharactersByName(name)
    }
}