package com.example.miniguide.routes.data

import com.example.miniguide.routes.presentation.pointSearch.model.PointModel
import com.mapbox.geojson.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoutesRepositoryImpl @Inject constructor() : RoutesRepository {

    private val startPointFlow = MutableSharedFlow<PointModel>(replay = 1, 0, BufferOverflow.DROP_OLDEST)

    private val endPointFlow = MutableSharedFlow<PointModel>(replay = 1, 0, BufferOverflow.DROP_OLDEST)

    override suspend fun setStartPoint(point: PointModel) = withContext(Dispatchers.Default) {
        startPointFlow.emit(point)
    }

    override suspend fun setEndPoint(point: PointModel) = withContext(Dispatchers.Default) {
        endPointFlow.emit(point)
    }

    override suspend fun getPointsToVisit(startPoint: PointModel, endPoint: PointModel): List<Point> = withContext(Dispatchers.Default) {
        val start = Point.fromLngLat(startPoint.longitude , startPoint.latitude)
        val end = Point.fromLngLat(endPoint.longitude, endPoint.latitude)
        val firstPoint = Point.fromLngLat(151.186335, -33.931479)
        val secondPoint = Point.fromLngLat(151.201670, -33.918223)
        val thirdPoint = Point.fromLngLat(151.178224, -33.922789)
        val fourthPoint = Point.fromLngLat(151.188383, -33.901126)
        val fifthPoint = Point.fromLngLat(151.203758, -33.890750)
        listOf<Point>(start, firstPoint, secondPoint, thirdPoint, fourthPoint, fifthPoint, end)
    }

    override fun selectStartPointFlow(): Flow<PointModel> = startPointFlow

    override fun selectEndPointFlow(): Flow<PointModel> = endPointFlow
}