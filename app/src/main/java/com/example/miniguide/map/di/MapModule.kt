package com.example.miniguide.map.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniguide.common.di.scope.ScreenScope
import com.example.miniguide.common.di.viewModel.ViewModelKey
import com.example.miniguide.map.presentation.MapViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MapModule {

    @ScreenScope
    @Provides
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    fun provideMapViewModel(

    ): ViewModel {
        return MapViewModel()
    }

    @ScreenScope
    @Provides
    fun provideMapViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): MapViewModel =
        ViewModelProvider(fragment, viewModelFactory).get(MapViewModel::class.java)
}