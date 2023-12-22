package com.example.onboardingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboardingapp.services.domains.CharacterRDomain
import com.example.onboardingapp.services.models.toDomain
import com.example.onboardingapp.services.repository.ServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetCharactersViewModel : ViewModel() {
    private val repository = ServiceRepository()

    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String> get() = _onError

    private val _getCharactersResponse = MutableLiveData<ArrayList<CharacterRDomain>>()
    val getCharactersResponse: LiveData<ArrayList<CharacterRDomain>> get() = _getCharactersResponse

    fun getCharacters() = viewModelScope.launch {
        repository.getCharacters().let { response ->
            try {
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        _getCharactersResponse.value = response.body()?.characters?.map {
                            it.toDomain()
                        } as ArrayList<CharacterRDomain>?
                    }
                }
            } catch (ex: Exception) {
                setOnError(ex.message ?: "Error with GetCharacters Service")
            }
        }
    }

    fun getCharactersByName(url: String) = viewModelScope.launch {
        repository.getCharactersByName(url).let { response ->
            try {
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        _getCharactersResponse.value = response.body()?.characters?.map {
                            it.toDomain()
                        } as ArrayList<CharacterRDomain>?
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        _getCharactersResponse.value = ArrayList()
                    }
                }
            } catch (ex: Exception) {
                setOnError(ex.message ?: "Error with GetCharactersByName Service")
            }
        }
    }

    private fun setOnError(message: String) = CoroutineScope(Dispatchers.Main).launch {
        _onError.value = message
    }
}