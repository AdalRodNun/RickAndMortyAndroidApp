package com.myapp.rickandmorty.framework

import com.myapp.rickandmorty.core.useCase.AddUser
import com.myapp.rickandmorty.core.useCase.GetUserByEmail
import com.myapp.rickandmorty.core.useCase.ValidateUser

data class UserUseCases (
    val addUser: AddUser,
    val getUserByEmail: GetUserByEmail,
    val validateUser: ValidateUser
)


