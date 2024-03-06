package com.myapp.rickandmorty.framework.di

import com.myapp.rickandmorty.ui.viewModel.LoginViewModel
import com.myapp.rickandmorty.ui.viewModel.RegisterViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(registerViewModel: RegisterViewModel)

    fun inject(loginViewModel: LoginViewModel)
}