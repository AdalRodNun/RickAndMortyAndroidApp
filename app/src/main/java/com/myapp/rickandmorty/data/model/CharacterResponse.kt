package com.myapp.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse (
        @SerializedName("info") val info: Info?,
        @SerializedName("results") val characters: ArrayList<CharacterModel>?
)