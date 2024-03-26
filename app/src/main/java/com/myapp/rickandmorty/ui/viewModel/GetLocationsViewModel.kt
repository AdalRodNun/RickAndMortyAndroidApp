package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.myapp.rickandmorty.domain.model.LocationR
import com.myapp.rickandmorty.domain.useCase.GetAllLocations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetLocationsViewModel @Inject constructor(
    private val getAllLocations: GetAllLocations
) : ViewModel() {

    private val _resultState = MutableStateFlow<Flow<PagingData<LocationR>>>(
        value = flowOf(PagingData.empty())
    )
    val resultsState: StateFlow<Flow<PagingData<LocationR>>> = _resultState

    init {
        getLocationsList(name = null)
    }

    fun getLocationsList(name: String?) = viewModelScope.launch {
        val pagerFlow = getAllLocations(locationName = name)
        _resultState.value = pagerFlow
    }
}