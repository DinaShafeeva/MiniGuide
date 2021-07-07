package com.example.miniguide.routes.domain

import com.example.miniguide.map.data.MapRepository
import com.example.miniguide.routes.data.RoutesRepository
import com.example.miniguide.routes.presentation.pointSearch.model.PointModel
import com.mapbox.geojson.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoutesInteractorImpl @Inject constructor(
    private val routesRepository: RoutesRepository,
    private val mapRepository: MapRepository
) : RoutesInteractor {

    override suspend fun setStartPoint(point: PointModel) {
        routesRepository.setStartPoint(point)
    }

    override suspend fun setEndPoint(point: PointModel) {
        routesRepository.setEndPoint(point)
    }

    override suspend fun createRoute(startPoint: PointModel, endPoint: PointModel) {
        val placesToVisit = routesRepository.getPointsToVisit(startPoint, endPoint)
        mapRepository.createRoute(placesToVisit)
    }

    override fun startPointFlow(): Flow<PointModel> = routesRepository.selectStartPointFlow()

    override fun endPointFlow(): Flow<PointModel> = routesRepository.selectEndPointFlow()
}