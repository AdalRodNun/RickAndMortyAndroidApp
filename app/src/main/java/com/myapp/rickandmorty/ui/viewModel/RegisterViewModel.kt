package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.domain.useCase.AddUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val addUser: AddUser
): ViewModel() {

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> get() = _saved


    fun saveUser(user: User) = viewModelScope.launch {
        addUser(user)
        _saved.postValue(true)
    }
}