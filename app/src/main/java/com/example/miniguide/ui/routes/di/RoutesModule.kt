package com.example.miniguide.ui.routes.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniguide.di.ViewModelKey
import com.example.miniguide.ui.routes.RoutesViewModel
import com.example.miniguide.ui.routes.di.scope.RoutesScope
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class RoutesModule {

    @RoutesScope
    @Provides
    @IntoMap
    @ViewModelKey(RoutesViewModel::class)
    fun provideMatchListViewModel(): ViewModel {
        return RoutesViewModel()
    }

    @RoutesScope
    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): RoutesViewModel =
        ViewModelProvider(fragment, viewModelFactory).get(RoutesViewModel::class.java)

}