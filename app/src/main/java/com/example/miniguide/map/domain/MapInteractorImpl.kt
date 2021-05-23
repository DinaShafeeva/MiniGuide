package com.example.miniguide.map.domain

import com.example.miniguide.map.data.MapRepository
import com.mapbox.api.directions.v5.models.DirectionsRoute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapInteractorImpl @Inject constructor(
    private val mapRepository: MapRepository
): MapInteractor {

    override fun startRouteFlow(): Flow<List<DirectionsRoute>> = mapRepository.selectDirectionsRouteFlow()
}