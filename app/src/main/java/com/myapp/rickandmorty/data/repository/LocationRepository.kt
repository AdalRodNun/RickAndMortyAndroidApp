package com.myapp.rickandmorty.data.repository

import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.data.model.LocationResponse
import com.myapp.rickandmorty.service.RickAndMortyClient
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val api: RickAndMortyClient
) {
    suspend fun getAllLocations(page: Int, locationName: String?): ApiResponse<LocationResponse> {
        return api.getAllLocations(page = page, locationName = locationName)
    }
}