package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.core.room.entities.CharacterEntity
import com.myapp.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class AddCharacter @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: CharacterEntity) {
        repository.addCharacterToDatabase(character)
    }
}