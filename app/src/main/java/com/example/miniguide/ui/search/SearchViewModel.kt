package com.example.miniguide.ui.search

import android.util.Log
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.miniguide.R
import com.example.miniguide.data.PointModel
import com.example.miniguide.domain.routes.RoutesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val interactor: RoutesInteractor) :
    ViewModel() {

    val currentStartPoint = interactor.startPointFlow()
        .flowOn(Dispatchers.Default)
        .share()

    val currentEndPoint = interactor.endPointFlow()
        .flowOn(Dispatchers.Default)
        .share()


    fun setStartPoint(point: PointModel) {
        Log.d("backkk", "fsf 1")
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

    var view: View? = null
    private fun goBack() {
        Log.d("backkk", "fsf")
        view?.findNavController()?.navigate(R.id.routesFragment)
    }

    //transfer to Base VM
    fun <T> Flow<T>.share() = shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}