package com.example.miniguide.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

//    @Provides
//    @Singleton
//    fun provideProfileRepository(remote: IProfileRemote, cache: IProfileCache): IProfileRepository =
//        ProfileRepository(remote, cache)

}