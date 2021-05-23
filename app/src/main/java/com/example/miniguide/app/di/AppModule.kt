package com.example.miniguide.app.di

import android.content.Context
import com.example.miniguide.app.di.scope.ApplicationScope
import com.example.miniguide.common.di.scope.ScreenScope
import com.example.miniguide.map.data.MapRepository
import com.example.miniguide.map.data.MapRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun provideAppContext(): Context = context

    @ApplicationScope
    @Provides
    fun provideMapRepository(mapRepository: MapRepositoryImpl): MapRepository =
        mapRepository

}