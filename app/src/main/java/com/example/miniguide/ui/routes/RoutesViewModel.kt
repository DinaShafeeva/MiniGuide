package com.example.miniguide.ui.routes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniguide.data.PointTypeModel
import com.example.miniguide.domain.routes.RoutesInteractor
import com.example.miniguide.ui.base.Navigator
import com.mapbox.search.result.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
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

    fun onCreateRouteClick(list: List<SearchResult>) {
        val array: DoubleArray = DoubleArray(list.size * 2 + 4)
        array.distinct()
        list.map {
            array[2 * list.indexOf(it)] = it.coordinate?.longitude() ?: 0.0
            array[2 * list.indexOf(it) + 1] = it.coordinate?.latitude() ?: 0.0
        }
        array[2*list.size] = currentStartPoint.replayCache.last().longitude
        array[2*list.size+1] = currentStartPoint.replayCache.last().latitude
        array[2*list.size+2] = currentEndPoint.replayCache.last().longitude
        array[2*list.size+3] = currentEndPoint.replayCache.last().latitude
        navigator.openMap(array)
        Log.d("listOfSR", list.toString())
//        viewModelScope.launch {
//            interactor.createRoute(currentStartPoint.replayCache.last(), currentEndPoint.replayCache.last())
//        }
    }

    //transfer to Base VM
    fun <T> Flow<T>.share() = shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}