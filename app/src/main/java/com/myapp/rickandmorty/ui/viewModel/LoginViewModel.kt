package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.domain.useCase.ValidateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUser: ValidateUser
): ViewModel() {

    private val _checked = MutableLiveData<Boolean>()
    val checked: LiveData<Boolean> get() = _checked


    fun checkUser(email: String, password: String) = viewModelScope.launch {
        val checked = validateUser(email, password)
        _checked.postValue(checked)
    }
}