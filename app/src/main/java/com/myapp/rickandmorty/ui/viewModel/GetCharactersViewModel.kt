package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.model.toDomain
import com.myapp.rickandmorty.domain.useCase.GetCharactersByName
import com.myapp.rickandmorty.domain.useCase.GetCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCharactersViewModel @Inject constructor(
    private val getCharacters: GetCharacters,
    private val getCharacterByName: GetCharactersByName
) : ViewModel() {

    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String> get() = _onError

    private val _getCharactersResponse = MutableLiveData<List<CharacterR>>()
    val getCharactersResponse: LiveData<List<CharacterR>> get() = _getCharactersResponse

    fun getCharacters() = viewModelScope.launch {
        when (val response = getCharacters.invoke()) {
            is ApiResponse.Loading -> {}
            is ApiResponse.Success -> {
                _getCharactersResponse.value = response.data?.characters?.map { it.toDomain() }
            }
            is ApiResponse.Error -> {
                setOnError(response.message ?: "ERROR NOT FOUND")
            }
        }
    }

    fun getCharactersByName(name: String) = viewModelScope.launch {
        when (val response = getCharacterByName(name)) {
            is ApiResponse.Loading -> {}
            is ApiResponse.Success -> {
                _getCharactersResponse.value = response.data?.characters?.map { it.toDomain() }
            }
            is ApiResponse.Error -> {
                setOnError(response.message ?: "ERROR NOT FOUND")
            }
        }
    }

    private fun setOnError(message: String) = CoroutineScope(Dispatchers.Main).launch {
        _onError.value = message
    }
}