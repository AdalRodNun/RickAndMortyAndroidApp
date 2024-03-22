package com.myapp.rickandmorty.domain.model

import com.myapp.rickandmorty.data.enums.LocationTypeEnum
import com.myapp.rickandmorty.data.enums.LocationTypeEnum.Companion.toLocationEnum
import com.myapp.rickandmorty.data.model.LocationModel

data class LocationR(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String,
    val typeIcon: LocationTypeEnum
)

fun LocationModel.toDomain() = LocationR(
    created = created,
    dimension = dimension,
    id = id,
    name = name,
    residents = residents,
    type = type,
    url = url,
    typeIcon = type.toLocationEnum()
)