package com.example.miniguide.app.di

import com.example.miniguide.app.di.scope.ApplicationScope
import com.example.miniguide.app.navigator.Navigator
import com.example.miniguide.map.router.MapRouter
import com.example.miniguide.routes.router.RoutesRouter
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideRoutesRouter(navigator: Navigator): RoutesRouter = navigator

    @ApplicationScope
    @Provides
    fun provideMapRouter(navigator: Navigator): MapRouter = navigator
}