package com.myapp.rickandmorty.service.paging

import dagger.assisted.AssistedFactory

@AssistedFactory
interface CharactersDataSourceFactory {
    fun create(name: String?): CharactersDataSource
}