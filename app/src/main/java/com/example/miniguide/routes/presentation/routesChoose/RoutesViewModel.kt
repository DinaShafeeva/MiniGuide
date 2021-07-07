package com.example.miniguide.routes.presentation.routesChoose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniguide.routes.domain.RoutesInteractor
import com.example.miniguide.routes.presentation.pointSearch.model.PointTypeModel
import com.example.miniguide.routes.router.RoutesRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class RoutesViewModel(
    private val interactor: RoutesInteractor,
    private val router: RoutesRouter
) : ViewModel() {

    val currentStartPoint = interactor.startPointFlow()
        .flowOn(Dispatchers.Default)
        .share()

    val currentEndPoint = interactor.endPointFlow()
        .flowOn(Dispatchers.Default)
        .share()

    fun openPointSearch(pointTypeModel: PointTypeModel) {
        router.openPointSearch(pointTypeModel)
    }

    fun onCreateRouteClick() {
        viewModelScope.launch {
            interactor.createRoute(currentStartPoint.replayCache.last(), currentEndPoint.replayCache.last())
        }
    }

    fun back() {
        router.back()
    }

    //transfer to Base VM
    fun <T> Flow<T>.share() = shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}