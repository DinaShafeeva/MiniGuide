package com.example.miniguide.map.data

import android.provider.Settings.System.getString
import com.example.miniguide.BuildConfig
import com.example.miniguide.R
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor() : MapRepository {

    private val route = MutableSharedFlow<List<DirectionsRoute>>(1, 0, BufferOverflow.DROP_OLDEST)

    override suspend fun createRoute(points: List<Point>) {
        withContext(Dispatchers.Default) {
            buildClient(points).executeCall().body()?.let {
                route.emit(it.routes())
            }
        }
    }

    override fun selectDirectionsRouteFlow(): Flow<List<DirectionsRoute>> = route

    private fun buildClient(points: List<Point>): MapboxDirections {
        val pointsSize = points.size
        val client = MapboxDirections.builder()
            .origin(points[0])
            .destination(points[pointsSize - 1])
            .steps(true)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(DirectionsCriteria.PROFILE_WALKING)
            .accessToken(BuildConfig.ACCESS_TOKEN)

        for (i in 1 until pointsSize) {
            client.addWaypoint(points[i])
        }
        return client.build()
    }

}