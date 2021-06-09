package com.example.miniguide.di

import com.example.miniguide.ui.base.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator = Navigator()
}