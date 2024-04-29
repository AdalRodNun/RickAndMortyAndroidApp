package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.core.dataStore.DataStoreManager
import com.myapp.rickandmorty.domain.useCase.ValidateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUserUseCase: ValidateUserUseCase,
    private val dataStore: DataStoreManager
): ViewModel() {

    private val _checked = MutableLiveData<Boolean>()
    val checked: LiveData<Boolean> get() = _checked

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            delay(2000)
            dataStore.getSessionID().collect {
                if (it == null) {
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                    _checked.value = true
                }
            }
        }
    }

    fun checkUser(email: String, password: String) = viewModelScope.launch {
        val checked = validateUserUseCase(email, password)
        _checked.postValue(checked)
    }
}