package com.example.miniguide.di

import android.content.Context
import com.example.miniguide.remote.routes.RoutesRepository
import com.example.miniguide.remote.routes.RoutesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideRoutesRepository(): RoutesRepository =
        RoutesRepositoryImpl()

}