package com.example.miniguide.ui.routes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniguide.data.PointTypeModel
import com.example.miniguide.domain.routes.RoutesInteractor
import com.example.miniguide.ui.base.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel  @Inject constructor(
    private val interactor: RoutesInteractor,
    private val navigator: Navigator
) : ViewModel() {

    val currentStartPoint = interactor.startPointFlow()
        .flowOn(Dispatchers.Default)
        .share()

    val currentEndPoint = interactor.endPointFlow()
        .flowOn(Dispatchers.Default)
        .share()

    fun openPointSearch(pointTypeModel: PointTypeModel) {
        navigator.openPointSearch(pointTypeModel)
    }

    fun onCreateRouteClick() {
        viewModelScope.launch {
            interactor.createRoute(currentStartPoint.replayCache.last(), currentEndPoint.replayCache.last())
        }
    }

    //transfer to Base VM
    fun <T> Flow<T>.share() = shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}