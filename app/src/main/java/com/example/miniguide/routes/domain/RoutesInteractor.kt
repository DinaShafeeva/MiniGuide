package com.example.miniguide.routes.domain

import com.example.miniguide.routes.presentation.pointSearch.model.PointModel
import kotlinx.coroutines.flow.Flow

interface RoutesInteractor {

    suspend fun setStartPoint(point: PointModel)

    suspend fun setEndPoint(point: PointModel)

    suspend fun createRoute(startPoint: PointModel, endPoint: PointModel)

    fun startPointFlow(): Flow<PointModel>

    fun endPointFlow(): Flow<PointModel>
}