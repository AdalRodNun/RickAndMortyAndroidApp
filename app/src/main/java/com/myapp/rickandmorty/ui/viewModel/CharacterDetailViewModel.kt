package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.core.retrofit.ApiResponse
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.domain.model.toDomain
import com.myapp.rickandmorty.domain.useCase.GetCharacterByID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterByID: GetCharacterByID
) : ViewModel() {

    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String> get() = _onError

    private val _characterResponse = MutableLiveData<CharacterR>()
    val characterResponse: LiveData<CharacterR> get() = _characterResponse

    fun getCharacterInfo(characterID: Int) = viewModelScope.launch {
        when (val response = getCharacterByID(characterID)) {
            is ApiResponse.Loading -> {
                //TODO Implement loading
            }
            is ApiResponse.Success -> {
                _characterResponse.value = response.data?.toDomain()
            }
            is ApiResponse.Error -> {
                setOnError(response.message ?: "ERROR NOT FOUND")
            }
        }
    }

    private fun setOnError(message: String) = viewModelScope.launch {
        _onError.value = message
    }
}