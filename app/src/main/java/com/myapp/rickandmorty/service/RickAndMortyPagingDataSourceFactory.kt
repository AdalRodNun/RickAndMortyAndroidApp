package com.myapp.rickandmorty.service

import dagger.assisted.AssistedFactory

@AssistedFactory
interface RickAndMortyPagingDataSourceFactory {
    fun create(name: String?): RickAndMortyPagingDataSource
}