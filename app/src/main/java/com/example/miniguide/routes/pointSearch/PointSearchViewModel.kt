package com.example.miniguide.routes.pointSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniguide.routes.domain.RoutesInteractor
import com.example.miniguide.routes.pointSearch.model.PointModel
import com.example.miniguide.routes.router.RoutesRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class PointSearchViewModel(
    private val interactor: RoutesInteractor,
    private val router: RoutesRouter
) : ViewModel() {

    val currentStartPoint = interactor.startPointFlow()
        .flowOn(Dispatchers.Default)
        .share()

    val currentEndPoint = interactor.endPointFlow()
        .flowOn(Dispatchers.Default)
        .share()


    fun setStartPoint(point: PointModel) {
        viewModelScope.launch {
            interactor.setStartPoint(point)
            goBack()
        }
    }

    fun setEndPoint(point: PointModel) {
        viewModelScope.launch {
            interactor.setEndPoint(point)
            goBack()
        }
    }

    private fun goBack() {
        router.back()
    }

    //transfer to Base VM
    fun <T> Flow<T>.share() = shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}