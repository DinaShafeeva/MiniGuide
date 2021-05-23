package com.example.miniguide.app.di

import com.example.miniguide.map.di.MapComponent
import com.example.miniguide.map.presentation.MapFragment
import com.example.miniguide.routes.di.RoutesComponent
import com.example.miniguide.routes.presentation.pointSearch.PointSearchFragment
import com.example.miniguide.routes.presentation.routesChoose.RoutesFragment

object Injector {

    private var routesComponent: RoutesComponent? = null
    private var mapComponent: MapComponent? = null

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

    fun plusMapComponent(fragment: MapFragment): MapComponent = mapComponent
        ?: App.appComponent.provideMapComponent()
            .withFragment(fragment)
            .build().also {
                mapComponent = it
            }

    fun clearMapComponent() {
        mapComponent = null

    }
}