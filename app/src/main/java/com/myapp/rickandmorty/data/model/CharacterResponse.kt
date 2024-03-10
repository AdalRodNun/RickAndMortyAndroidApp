package com.myapp.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse (
        @SerializedName("count") val count: Int?,
        @SerializedName("pages") val pages: Int?,
        @SerializedName("next") val nextPage: String?,
        @SerializedName("prev") val prevPage: String?,
        @SerializedName("results") val characters: ArrayList<CharacterModel>?
)