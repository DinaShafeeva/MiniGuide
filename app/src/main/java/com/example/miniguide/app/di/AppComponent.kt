package com.example.miniguide.app.di

import com.example.miniguide.app.di.scope.ApplicationScope
import com.example.miniguide.root.RootActivity
import com.example.miniguide.routes.di.RoutesComponent
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class, NavigationModule::class])
interface AppComponent {

    fun provideRoutesComponent(): RoutesComponent.Builder

    fun inject(activity: RootActivity)
}