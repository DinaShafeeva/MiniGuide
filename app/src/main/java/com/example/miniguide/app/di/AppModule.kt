package com.example.miniguide.app.di

import android.content.Context
import com.example.miniguide.app.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun provideAppContext(): Context = context

}