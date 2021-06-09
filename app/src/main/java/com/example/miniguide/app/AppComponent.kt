package com.example.miniguide.app

import com.example.miniguide.MainActivity
import com.example.miniguide.di.AppModule
import com.example.miniguide.di.RemoteModule
import com.example.miniguide.di.ViewModelFactoryModule
import com.example.miniguide.di.ViewModelModule
import com.example.miniguide.ui.map.MapFragment
import com.example.miniguide.ui.routes.RoutesFragment
import com.example.miniguide.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RemoteModule::class, ViewModelModule::class, ViewModelFactoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: RoutesFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: MapFragment)

}