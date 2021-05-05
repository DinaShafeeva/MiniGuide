package com.example.miniguide.app.di

import com.example.miniguide.routes.di.RoutesComponent
import com.example.miniguide.routes.pointSearch.PointSearchFragment
import com.example.miniguide.routes.routesChoose.RoutesFragment

object Injector {

    private var routesComponent: RoutesComponent? = null

    fun plusRoutesComponent(fragment: RoutesFragment): RoutesComponent = routesComponent
        ?: App.appComponent.provideRoutesComponent()
            .withFragment(fragment)
            .build().also {
                routesComponent = it
            }

    fun plusRoutesComponent(fragment: PointSearchFragment): RoutesComponent = routesComponent
        ?: App.appComponent.provideRoutesComponent()
            .withFragment(fragment)
            .build().also {
                routesComponent = it
            }

    fun clearRoutesComponent() {
        routesComponent = null
    }
}