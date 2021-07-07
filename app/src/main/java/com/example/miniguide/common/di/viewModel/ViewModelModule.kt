package com.example.miniguide.common.di.viewModel

import androidx.lifecycle.ViewModelProvider
import com.example.miniguide.app.di.scope.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}
