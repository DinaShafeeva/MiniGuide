package com.example.miniguide.domain.routes

import com.example.miniguide.data.PointModel
import com.example.miniguide.remote.routes.RoutesRepository
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

    override fun startPointFlow(): Flow<PointModel> = routesRepository.selectStartPointFlow()

    override fun endPointFlow(): Flow<PointModel> = routesRepository.selectEndPointFlow()
}