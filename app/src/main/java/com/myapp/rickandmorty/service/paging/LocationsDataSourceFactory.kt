package com.myapp.rickandmorty.service.paging

import dagger.assisted.AssistedFactory

@AssistedFactory
interface LocationsDataSourceFactory {
    fun create(name: String?): LocationsDataSource
}