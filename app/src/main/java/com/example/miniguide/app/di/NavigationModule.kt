package com.example.miniguide.app.di

import com.example.miniguide.app.navigator.Navigator
import com.example.miniguide.ui.routes.router.RoutesRouter
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @Provides
    fun provideRoutesRouter(navigator: Navigator): RoutesRouter = navigator
}