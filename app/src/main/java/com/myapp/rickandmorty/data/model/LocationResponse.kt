package com.myapp.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val locations: List<LocationModel>
)