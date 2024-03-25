package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.core.room.DBResponse.Success
import com.myapp.rickandmorty.core.room.DBResponse.Error
import com.myapp.rickandmorty.domain.useCase.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase
): ViewModel() {

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> get() = _saved

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    fun saveUser(user: User) = viewModelScope.launch {
        when(val response = addUserUseCase(user)) {
            is Success -> _saved.postValue(true)
            is Error -> _message.value = response.message
        }
    }
}