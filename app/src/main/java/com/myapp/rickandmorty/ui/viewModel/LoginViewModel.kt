package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.domain.useCase.ValidateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUser: ValidateUser
): ViewModel() {

    private val _checked = MutableLiveData<Boolean>()
    val checked: LiveData<Boolean> get() = _checked

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            delay(2000)
            _isLoading.value = false
        }
    }

    fun checkUser(email: String, password: String) = viewModelScope.launch {
        val checked = validateUser(email, password)
        _checked.postValue(checked)
    }
}