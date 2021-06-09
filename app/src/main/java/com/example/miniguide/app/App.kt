package com.example.miniguide.app

import android.app.Application
import com.mapbox.search.MapboxSearchSdk
import com.mapbox.search.location.DefaultLocationProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MapboxSearchSdk.initialize(
            application = this,
            accessToken = "pk.eyJ1IjoiZXBpY3VzIiwiYSI6ImNrbzRubnJ1YjFremsycXBnczk0ZnV0NWYifQ.kR8J7q7vH7sqI9U5vdF-rA",
            locationProvider = DefaultLocationProvider(this)
        )
    }
}