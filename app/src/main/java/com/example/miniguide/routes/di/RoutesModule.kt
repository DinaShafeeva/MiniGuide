package com.example.miniguide.routes.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniguide.routes.data.RoutesRepository
import com.example.miniguide.routes.data.RoutesRepositoryImpl
import com.example.miniguide.routes.domain.RoutesInteractor
import com.example.miniguide.routes.domain.RoutesInteractorImpl
import com.example.miniguide.common.di.scope.ScreenScope
import com.example.miniguide.common.di.viewModel.ViewModelKey
import com.example.miniguide.map.data.MapRepository
import com.example.miniguide.map.data.MapRepositoryImpl
import com.example.miniguide.map.domain.MapInteractor
import com.example.miniguide.map.domain.MapInteractorImpl
import com.example.miniguide.routes.presentation.pointSearch.PointSearchViewModel
import com.example.miniguide.routes.router.RoutesRouter
import com.example.miniguide.routes.presentation.routesChoose.RoutesViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class RoutesModule {

    @ScreenScope
    @Provides
    fun provideRoutesInteractor(routesInteractor: RoutesInteractorImpl): RoutesInteractor =
        routesInteractor

    @ScreenScope
    @Provides
    fun provideRoutesRepository(routesRepository: RoutesRepositoryImpl): RoutesRepository =
        routesRepository

    @ScreenScope
    @Provides
    @IntoMap
    @ViewModelKey(PointSearchViewModel::class)
    fun providePointSearchViewModel(
        routesInteractor: RoutesInteractor,
        routesRouter: RoutesRouter
    ): ViewModel {
        return PointSearchViewModel(routesInteractor, routesRouter)
    }

    @ScreenScope
    @Provides
    fun providePointSearcherViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): PointSearchViewModel =
        ViewModelProvider(fragment, viewModelFactory).get(PointSearchViewModel::class.java)

    @ScreenScope
    @Provides
    @IntoMap
    @ViewModelKey(RoutesViewModel::class)
    fun provideRoutesChooseViewModel(
        routesInteractor: RoutesInteractor,
        routesRouter: RoutesRouter
    ): ViewModel {
        return RoutesViewModel(routesInteractor, routesRouter)
    }

    @ScreenScope
    @Provides
    fun provideRoutesViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): RoutesViewModel =
        ViewModelProvider(fragment, viewModelFactory).get(RoutesViewModel::class.java)
}