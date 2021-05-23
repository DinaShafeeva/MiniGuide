package com.example.miniguide.routes.data

import com.example.miniguide.routes.presentation.pointSearch.model.PointModel
import com.mapbox.geojson.Point
import kotlinx.coroutines.flow.Flow

interface RoutesRepository {

    suspend fun setStartPoint(point: PointModel)

    suspend fun setEndPoint(point: PointModel)

    suspend fun getPointsToVisit(startPoint: PointModel, endPoint: PointModel): List<Point>

    fun selectStartPointFlow(): Flow<PointModel>

    fun selectEndPointFlow(): Flow<PointModel>
}