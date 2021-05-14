package com.example.miniguide.app

import android.app.Application
import com.example.miniguide.di.AppModule
import com.mapbox.search.MapboxSearchSdk
import com.mapbox.search.location.DefaultLocationProvider

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        MapboxSearchSdk.initialize(
            application = this,
            accessToken = "pk.eyJ1IjoiZXBpY3VzIiwiYSI6ImNrbzRubnJ1YjFremsycXBnczk0ZnV0NWYifQ.kR8J7q7vH7sqI9U5vdF-rA",
            locationProvider = DefaultLocationProvider(this)
        )
    }
}