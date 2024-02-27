package com.myapp.rickandmorty.services.models

import com.google.gson.annotations.SerializedName
import com.myapp.rickandmorty.services.domains.CharacterRDomain

data class CharacterR (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("origin") val origin: Origin?,
    @SerializedName("location") val location: Location?,
    @SerializedName("image") val image: String?,
    @SerializedName("episode") val episodes: ArrayList<String>?,
    @SerializedName("url") val url: String?,
    @SerializedName("created") val created: String?
)

data class Origin (
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

data class Location (
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

fun CharacterR.toDomain() = CharacterRDomain(
    name = name,
    image = image
)