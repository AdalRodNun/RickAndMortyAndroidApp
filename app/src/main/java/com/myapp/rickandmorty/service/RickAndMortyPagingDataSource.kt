package com.myapp.rickandmorty.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.model.toDomain
import com.myapp.rickandmorty.domain.useCase.GetCharacters
import javax.inject.Inject

class RickAndMortyPagingDataSource @Inject constructor(private val getCharacters: GetCharacters) :
    PagingSource<Int, CharacterR>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterR>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterR> {
        val position = params.key ?: 1

        return when (val response = getCharacters(position)) {
            is ApiResponse.Error -> LoadResult.Error(Exception(response.toString()))
            is ApiResponse.Loading -> LoadResult.Error(Exception())
            is ApiResponse.Success -> {
                LoadResult.Page(
                    data = if (response.data?.characters?.isEmpty() == true) emptyList() else response.data?.characters!!.toList().map { it.toDomain() },
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (response.data?.characters?.isEmpty() == true) null else position + 1
                )
            }

        }
    }
}