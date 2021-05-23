package com.example.miniguide.map.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniguide.common.di.scope.ScreenScope
import com.example.miniguide.common.di.viewModel.ViewModelKey
import com.example.miniguide.map.data.MapRepository
import com.example.miniguide.map.data.MapRepositoryImpl
import com.example.miniguide.map.domain.MapInteractor
import com.example.miniguide.map.domain.MapInteractorImpl
import com.example.miniguide.map.presentation.MapViewModel
import com.example.miniguide.map.router.MapRouter
import com.example.miniguide.routes.data.RoutesRepository
import com.example.miniguide.routes.data.RoutesRepositoryImpl
import com.example.miniguide.routes.domain.RoutesInteractor
import com.example.miniguide.routes.domain.RoutesInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MapModule {

    @ScreenScope
    @Provides
    fun provideMapInteractor(mapInteractor: MapInteractorImpl): MapInteractor =
        mapInteractor

    @ScreenScope
    @Provides
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    fun provideMapViewModel(
        mapInteractor: MapInteractor,
        mapRouter: MapRouter
    ): ViewModel {
        return MapViewModel(mapInteractor, mapRouter)
    }

    @ScreenScope
    @Provides
    fun provideMapViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): MapViewModel =
        ViewModelProvider(fragment, viewModelFactory).get(MapViewModel::class.java)
}