package com.myapp.rickandmorty.domain.model

import com.myapp.rickandmorty.core.room.entities.CharacterEntity
import com.myapp.rickandmorty.data.model.CharacterModel
import java.util.UUID

data class CharacterR(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val gender: String?,
    val origin: String?,
    val location: String?,
    val image: String?,
    val episodes: ArrayList<String>?,
    val created: String?
)

fun CharacterModel.toDomain() = CharacterR(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    origin = origin?.name,
    location = location?.name,
    image = image,
    episodes = null,
    created = created
)

fun CharacterEntity.toDomain() = CharacterR(
    id = id.toInt(),
    name = name,
    status = status,
    species = species,
    gender = gender,
    origin = origin,
    location = location,
    image = image,
    episodes = null,
    created = created
)

fun CharacterR.toEntity(userUUID: UUID) = CharacterEntity(
    userUUID = userUUID,
    name = name,
    status = status,
    species = species,
    gender = gender,
    origin = origin,
    location = location,
    image = image,
    created = created
)

