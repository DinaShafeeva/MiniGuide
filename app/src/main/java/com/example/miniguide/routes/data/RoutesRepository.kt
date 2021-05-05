package com.example.miniguide.routes.data

import com.example.miniguide.routes.pointSearch.model.PointModel
import kotlinx.coroutines.flow.Flow

interface RoutesRepository {

    suspend fun setStartPoint(point: PointModel)

    suspend fun setEndPoint(point: PointModel)

    fun selectStartPointFlow(): Flow<PointModel>

    fun selectEndPointFlow(): Flow<PointModel>
}