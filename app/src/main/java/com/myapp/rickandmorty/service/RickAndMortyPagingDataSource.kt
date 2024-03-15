package com.myapp.rickandmorty.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.data.repository.CharacterRepository
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.model.toDomain
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val STARTING_PAGE_INDEX = 1

class RickAndMortyPagingDataSource @AssistedInject constructor(
    private val repository: CharacterRepository,
    @Assisted private val nameQuery: String?
) : PagingSource<Int, CharacterR>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterR>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterR> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return when (val response = repository.getAllCharacters(page = position, characterName = nameQuery)) {
            is ApiResponse.Error -> LoadResult.Error(Exception(response.toString()))
            is ApiResponse.Loading -> LoadResult.Error(Exception())
            is ApiResponse.Success -> {
                LoadResult.Page(
                    data = if (response.data?.characters?.isEmpty() == true) emptyList() else response.data?.characters!!.toList()
                        .map { it.toDomain() },
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.data?.characters?.isEmpty() == true) null else position + 1
                )
            }

        }
    }
}