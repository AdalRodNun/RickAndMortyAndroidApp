package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.model.toDomain
import com.myapp.rickandmorty.domain.useCase.GetCharactersByName
import com.myapp.rickandmorty.domain.useCase.GetCharacters
import com.myapp.rickandmorty.service.RickAndMortyPagingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCharactersViewModel @Inject constructor(
    private val getCharacters: GetCharacters,
    private val getCharacterByName: GetCharactersByName,
    private val rickAndMortyPagingDataSource: RickAndMortyPagingDataSource
) : ViewModel() {

    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String> get() = _onError

    private val _getCharactersResponse = MutableLiveData<List<CharacterR>>()
    val getCharactersResponse: LiveData<List<CharacterR>> get() = _getCharactersResponse

    val getCharactersResponse2 = Pager(PagingConfig(pageSize = 20)) {
            rickAndMortyPagingDataSource
        }.liveData.cachedIn(viewModelScope)


    init {
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

    fun getCharactersByName(name: String) = viewModelScope.launch {
        when (val response = getCharacterByName(name)) {
            is ApiResponse.Loading -> {}
            is ApiResponse.Success -> {
                _getCharactersResponse.value = response.data?.characters?.map { it.toDomain() }
            }
            is ApiResponse.Error -> {
                setOnError(response.message ?: "ERROR NOT FOUND")
                _getCharactersResponse.value = emptyList()
            }
        }
    }

    private fun setOnError(message: String) = viewModelScope.launch {
        _onError.value = message
    }
}