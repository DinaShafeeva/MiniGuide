package com.example.miniguide.domain.routes

import com.example.miniguide.data.PointModel
import com.example.miniguide.remote.routes.RoutesRepository
import com.mapbox.geojson.Point
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutesInteractorImpl @Inject constructor(
    private val routesRepository: RoutesRepository
) : RoutesInteractor {

    override suspend fun setStartPoint(point: PointModel) {
        routesRepository.setStartPoint(point)
    }

    override suspend fun setEndPoint(point: PointModel) {
        routesRepository.setEndPoint(point)
    }

    override suspend fun createRoute(startPoint: PointModel, endPoint: PointModel) {
//        val listOfPoints: MutableList<Point> = mutableListOf()
//        val placesToVisit = routesRepository.getPointsToVisit(startPoint, endPoint).points
//        placesToVisit.forEach {
//            listOfPoints.add(Point.fromLngLat(it[0], it[1]))
//        }
//        mapRepository.createRoute(listOfPoints)
    }

    override fun startPointFlow(): Flow<PointModel> = routesRepository.selectStartPointFlow()

    override fun endPointFlow(): Flow<PointModel> = routesRepository.selectEndPointFlow()
}