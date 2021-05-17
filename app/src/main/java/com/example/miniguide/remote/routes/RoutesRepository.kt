package com.example.miniguide.remote.routes

import com.example.miniguide.data.PointModel
import kotlinx.coroutines.flow.Flow

interface RoutesRepository {

    suspend fun setStartPoint(point: PointModel)

    suspend fun setEndPoint(point: PointModel)

    fun selectStartPointFlow(): Flow<PointModel>

    fun selectEndPointFlow(): Flow<PointModel>
}