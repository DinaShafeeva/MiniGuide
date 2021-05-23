package com.example.miniguide.map.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniguide.map.domain.MapInteractor
import com.example.miniguide.map.router.MapRouter
import com.example.miniguide.routes.router.RoutesRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn

class MapViewModel(
    private val interactor: MapInteractor,
    private val router: MapRouter
) : ViewModel() {

    val currentRoute = interactor.startRouteFlow()
        .flowOn(Dispatchers.Default)
        .share()

    fun onRoutesClick() {
        router.openRoutesChoose()
    }

    //transfer to Base VM
    fun <T> Flow<T>.share() = shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 0)

}