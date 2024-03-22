package com.myapp.rickandmorty.domain.useCase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myapp.rickandmorty.domain.model.LocationR
import com.myapp.rickandmorty.service.paging.LocationsDataSourceFactory
import com.myapp.rickandmorty.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllLocations @Inject constructor() {

    @Inject
    lateinit var dataSourceFactory: LocationsDataSourceFactory

    operator fun invoke(locationName: String?) = setPager(name = locationName)

    private fun setPager(name: String?): Flow<PagingData<LocationR>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGING_NUMBER),
            pagingSourceFactory = {
                dataSourceFactory.create(name = name)
            }
        ).flow.flowOn(Dispatchers.IO)
    }
}