package com.myapp.rickandmorty.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.rickandmorty.framework.UserUseCases
import com.myapp.rickandmorty.framework.di.ApplicationModule
import com.myapp.rickandmorty.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel: ViewModel() {

    private val _checked = MutableLiveData<Boolean>()
    val checked: LiveData<Boolean> get() = _checked

    @Inject
    lateinit var useCases: UserUseCases

    init {
        @Suppress("DEPRECATION")
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(Application()))
            .build()
            .inject(this)
    }

    fun checkUser(email: String, password: String) = viewModelScope.launch {
        val checked = useCases.validateUser.invoke(email, password)
        _checked.postValue(checked)
    }
}