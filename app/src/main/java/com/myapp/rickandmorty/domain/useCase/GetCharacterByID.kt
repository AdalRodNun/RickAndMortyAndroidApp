package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterByID @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int) = repository.getCharacterByID(id)
}