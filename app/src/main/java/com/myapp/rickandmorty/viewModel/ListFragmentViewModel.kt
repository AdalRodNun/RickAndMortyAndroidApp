package com.myapp.rickandmorty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.rickandmorty.room.models.Person

class ListFragmentViewModel : ViewModel() {
    private val _listPersons = MutableLiveData<List<Person>>()
    val listPersons: LiveData<List<Person>> get() = _listPersons

    fun updatePersons(listPersons: List<Person>) {
        _listPersons.value = listPersons
    }
}