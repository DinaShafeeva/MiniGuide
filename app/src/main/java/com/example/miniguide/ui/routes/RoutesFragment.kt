package com.example.miniguide.ui.routes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.miniguide.R
import com.example.miniguide.data.PointModel
import com.example.miniguide.data.PointTypeModel
import com.example.miniguide.ui.base.BaseFragment
import com.mapbox.geojson.Point
import com.mapbox.search.*
import com.mapbox.search.result.SearchResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_routes.*


@AndroidEntryPoint
class RoutesFragment : BaseFragment<RoutesViewModel>() {
    private val viewModel: RoutesViewModel by viewModels()
    private lateinit var categorySearchEngine: CategorySearchEngine
    private lateinit var searchRequestTask: SearchRequestTask
    private var startPoint: PointModel? = null
    private var endPoint: PointModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }

    override fun subscribe() {
        viewModel.currentStartPoint.observe {
            Log.e("currentStartPoint - ", it.toString())
            tvStartPoint.text = context?.getString(R.string.point_text, it.name, it.location)
            startPoint = it
            pointList.clear()
            getPlaces()
        }
        viewModel.currentEndPoint.observe {
            Log.e("currentEndPoint - ", it.toString())
            tvEndPoint.text = context?.getString(R.string.point_text, it.name, it.location)
            endPoint = it
            getPlaces()
        }
    }

    override fun initClickListeners() {
        tvStartPoint.setOnClickListener {
            viewModel.openPointSearch(PointTypeModel.START_POINT)
        }
        tvEndPoint.setOnClickListener {
            viewModel.openPointSearch(PointTypeModel.END_POINT)
        }
        button.setOnClickListener {
            viewModel.onCreateRouteClick(pointList)
        }
    }

    private fun getPlaces() {
        categorySearchEngine = MapboxSearchSdk.createCategorySearchEngine()
        Log.i("SearchApiExample", startPoint?.longitude.toString() + " " + startPoint?.latitude)

        val options: CategorySearchOptions = CategorySearchOptions.Builder()
            .proximity(Point.fromLngLat(startPoint?.longitude ?: 0.0, startPoint?.latitude ?: 0.0))
            .build()

        searchRequestTask = categorySearchEngine.search(
            "historic",
            options,
            searchCallback
        )
    }

    var pointList: MutableList<SearchResult> = emptyList<SearchResult>().toMutableList()
    private val searchCallback: SearchCallback = object : SearchCallback {

        override fun onResults(results: List<SearchResult>, responseInfo: ResponseInfo) {
            if (results.isEmpty()) {
                Log.i("SearchApiExample", "No category search results")
            } else {
                Log.i("SearchApiExample", "Category search results: $results")
                pointList.addAll(results.take(5))
            }
        }

        override fun onError(e: Exception) {
            Log.i("SearchApiExample", "Search error", e)
        }
    }

    override fun initViews() {
    }
}