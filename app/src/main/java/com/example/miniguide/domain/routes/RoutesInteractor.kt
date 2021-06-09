package com.example.miniguide.domain.routes

import com.example.miniguide.data.PointModel
import kotlinx.coroutines.flow.Flow

interface RoutesInteractor {

    suspend fun setStartPoint(point: PointModel)

    suspend fun setEndPoint(point: PointModel)

    fun startPointFlow(): Flow<PointModel>

    fun endPointFlow(): Flow<PointModel>
}