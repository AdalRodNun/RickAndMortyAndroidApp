package com.myapp.rickandmorty.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel : ViewModel() {
    private val _enabledButton = MutableLiveData<Boolean>()
    val enabledButton: LiveData<Boolean> get() = _enabledButton

    fun validateFields(
        name: Boolean,
        address: Boolean,
        phone: Boolean,
        birthday: Boolean,
        photo: Boolean
    ) {
        _enabledButton.value = name && address && phone && birthday && photo
    }
}