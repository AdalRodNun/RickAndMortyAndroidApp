package com.myapp.rickandmorty.domain.useCase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.service.RickAndMortyPagingDataSourceFactory
import com.myapp.rickandmorty.utils.Constants.PAGING_NUMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllCharacters @Inject constructor() {

    @Inject lateinit var dataSourceFactory: RickAndMortyPagingDataSourceFactory

    operator fun invoke(characterName: String?) = setPager(name = characterName)

    private fun setPager(name: String?): Flow<PagingData<CharacterR>> {
        return Pager(
            config = PagingConfig(pageSize = PAGING_NUMBER),
            pagingSourceFactory = {
                dataSourceFactory.create(name = name)
            }
        ).flow.flowOn(Dispatchers.IO)
    }
}