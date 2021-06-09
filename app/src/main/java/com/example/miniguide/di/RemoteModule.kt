package com.example.miniguide.di

import com.example.miniguide.domain.routes.RoutesInteractor
import com.example.miniguide.domain.routes.RoutesInteractorImpl
import com.example.miniguide.remote.routes.RoutesRepository
import com.example.miniguide.remote.routes.RoutesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideRoutesRepository(): RoutesRepository =
        RoutesRepositoryImpl()

    @Provides
    @Singleton
    fun provideRoutesInteractor(repository: RoutesRepository): RoutesInteractor =
        RoutesInteractorImpl(repository)

}