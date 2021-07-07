package com.example.miniguide.map.data

import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    suspend fun createRoute(points: List<Point>)

    fun selectDirectionsRouteFlow(): Flow<List<DirectionsRoute>>

}