package com.myapp.rickandmorty.service.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.data.repository.LocationRepository
import com.myapp.rickandmorty.domain.model.LocationR
import com.myapp.rickandmorty.domain.model.toDomain
import com.myapp.rickandmorty.utils.Constants.STARTING_PAGE_INDEX
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class LocationsDataSource @AssistedInject constructor(
    private val repository: LocationRepository,
    @Assisted private val nameQuery: String?
) : PagingSource<Int, LocationR>() {

    override fun getRefreshKey(state: PagingState<Int, LocationR>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationR> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return when (val response = repository.getAllLocations(page = position, locationName = nameQuery)) {
            is ApiResponse.Error -> LoadResult.Error(Exception(response.toString()))
            is ApiResponse.Loading -> LoadResult.Error(Exception())
            is ApiResponse.Success -> {
                LoadResult.Page(
                    data = if (response.data?.locations?.isEmpty() == true) emptyList() else response.data?.locations!!.toList()
                        .map { it.toDomain() },
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.data?.info?.nextPage.isNullOrBlank()) null else position + 1
                )
            }
        }
    }
}