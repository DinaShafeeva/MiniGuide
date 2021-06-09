package com.example.miniguide.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniguide.data.PointModel
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
class SearchViewModel @Inject constructor(
    private val interactor: RoutesInteractor,
    val navigator: Navigator
) :
    ViewModel() {

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
        navigator.back()
    }

    //transfer to Base VM
    fun <T> Flow<T>.share() = shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}