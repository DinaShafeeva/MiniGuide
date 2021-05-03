package com.example.miniguide.app.di

import com.example.miniguide.ui.root.RootActivity
import com.example.miniguide.di.AppModule
import com.example.miniguide.di.viewModel.ViewModelModule
import com.example.miniguide.ui.routes.di.RoutesComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, NavigationModule::class])
interface AppComponent {

    fun provideRoutesComponent(): RoutesComponent.Builder

    fun inject(activity: RootActivity)
}