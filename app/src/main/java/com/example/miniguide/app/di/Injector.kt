package com.example.miniguide.app.di

import com.example.miniguide.ui.routes.RoutesFragment
import com.example.miniguide.ui.routes.di.RoutesComponent

object Injector {

    private var routesComponent: RoutesComponent? = null

    fun plusRoutesComponent(fragment: RoutesFragment): RoutesComponent = routesComponent
        ?: App.appComponent.provideRoutesComponent()
            .withFragment(fragment)
            .build().also {
                routesComponent = it
            }

    fun clearRoutesComponent() {
        routesComponent = null
    }
}