package com.example.miniguide.di

import com.example.miniguide.domain.routes.RoutesInteractor
import com.example.miniguide.domain.routes.RoutesInteractorImpl
import com.example.miniguide.remote.routes.RoutesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideRoutesInteractor(repository: RoutesRepository): RoutesInteractor =
        RoutesInteractorImpl(repository)
}