package com.myapp.rickandmorty.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.framework.UserUseCases
import com.myapp.rickandmorty.framework.di.ApplicationModule
import com.myapp.rickandmorty.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel: ViewModel() {

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> get() = _saved

    @Inject
    lateinit var useCases: UserUseCases

    init {
        @Suppress("DEPRECATION")
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(Application()))
            .build()
            .inject(this)
    }

    fun saveUser(user: User) = viewModelScope.launch {
        useCases.addUser(user)
        _saved.postValue(true)
    }
}