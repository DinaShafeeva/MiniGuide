package com.example.miniguide.map.domain

import com.mapbox.api.directions.v5.models.DirectionsRoute
import kotlinx.coroutines.flow.Flow

interface MapInteractor {

    fun startRouteFlow(): Flow<List<DirectionsRoute>>

}