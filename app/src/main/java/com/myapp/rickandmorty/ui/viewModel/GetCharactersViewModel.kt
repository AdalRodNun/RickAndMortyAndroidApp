package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.useCase.GetAllCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCharactersViewModel @Inject constructor(
    private val getAllCharacters: GetAllCharacters
) : ViewModel() {

    private val _resultState = MutableStateFlow<Flow<PagingData<CharacterR>>>(
        value = flowOf(PagingData.empty())
    )
    val resultsState: StateFlow<Flow<PagingData<CharacterR>>> = _resultState

    init {
        getCharactersList(name = null)
    }

    fun getCharactersList(name: String?) = viewModelScope.launch {
        val pagerFlow = getAllCharacters(characterName = name).cachedIn(viewModelScope)
        _resultState.value = pagerFlow
    }

    /*fun getCharacters() = viewModelScope.launch {
        when (val response = getCharacters(1)) {
            is ApiResponse.Loading -> {}
            is ApiResponse.Success -> {
                _getCharactersResponse.value = response.data?.characters?.map { it.toDomain() }
            }
            is ApiResponse.Error -> {
                setOnError(response.message ?: "ERROR NOT FOUND")
                _getCharactersResponse.value = emptyList()
            }
        }
    }*/
}