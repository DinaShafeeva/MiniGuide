package com.example.miniguide.app

import com.example.miniguide.MainActivity
import com.example.miniguide.di.AppModule
import com.example.miniguide.di.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}