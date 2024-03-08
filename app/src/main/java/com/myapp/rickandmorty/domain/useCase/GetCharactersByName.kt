package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.data.repository.CharacterRepository
import com.myapp.rickandmorty.domain.model.CharacterR
import javax.inject.Inject

class GetCharactersByName @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(name: String): List<CharacterR> {
        return repository.getCharactersByName(name)
    }
}