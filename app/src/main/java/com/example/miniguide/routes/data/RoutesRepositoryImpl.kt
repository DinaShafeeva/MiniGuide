package com.example.miniguide.routes.data

import com.example.miniguide.routes.presentation.pointSearch.model.PointModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoutesRepositoryImpl @Inject constructor() : RoutesRepository {

    private val startPointFlow = MutableSharedFlow<PointModel>(replay = 1)

    private val endPointFlow = MutableSharedFlow<PointModel>(replay = 1)

    override suspend fun setStartPoint(point: PointModel) = withContext(Dispatchers.Default) {
        startPointFlow.emit(point)
    }

    override suspend fun setEndPoint(point: PointModel) = withContext(Dispatchers.Default) {
        endPointFlow.emit(point)
    }

    override fun selectStartPointFlow(): Flow<PointModel> = startPointFlow

    override fun selectEndPointFlow(): Flow<PointModel> = endPointFlow
}