package com.example.miniguide.ui.routes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel  @Inject constructor(
//    private val interactor: RoutesInteractor,
//    private val router: RoutesRouter
) : ViewModel() {
//    val currentStartPoint = interactor.startPointFlow()
//        .flowOn(Dispatchers.Default)
//        .share()
//
//    val currentEndPoint = interactor.endPointFlow()
//        .flowOn(Dispatchers.Default)
//        .share()

//    fun openPointSearch(pointTypeModel: PointTypeModel) {
//        router.openPointSearch(pointTypeModel)
//    }
//
//    //transfer to Base VM
//    fun <T> Flow<T>.share() = shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}