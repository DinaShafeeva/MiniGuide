package com.example.miniguide.routes.presentation.pointSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniguide.R
import com.example.miniguide.app.di.Injector
import com.example.miniguide.app.navigator.Navigator
import com.example.miniguide.common.base.BaseFragment
import com.example.miniguide.common.utils.KeyboardUtils
import com.example.miniguide.routes.presentation.pointSearch.adapter.PointsAdapter
import com.example.miniguide.routes.presentation.pointSearch.model.PointModel
import com.example.miniguide.routes.presentation.pointSearch.model.PointTypeModel
import com.mapbox.search.*
import com.mapbox.search.result.SearchResult
import com.mapbox.search.result.SearchSuggestion
import kotlinx.android.synthetic.main.fragment_point_search.*
import javax.inject.Inject


private const val KEY_POINT_TYPE = "KEY_POINT_TYPE"

class PointSearchFragment : BaseFragment<PointSearchViewModel>() {

    private val searchEngine = MapboxSearchSdk.createSearchEngine()

    companion object {
        fun getBundle(pointTypeModel: PointTypeModel) = Bundle().apply {
            putParcelable(KEY_POINT_TYPE, pointTypeModel)
        }
    }

    @Inject
    lateinit var navigator: Navigator

    private var adapter: PointsAdapter? = null

    private var pointType: PointTypeModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_point_search, container, false)
    }

    override fun inject() {
        pointType = requireArguments()[KEY_POINT_TYPE] as PointTypeModel
        Injector.plusRoutesComponent(this).inject(this)
    }

    override fun initViews() {
        tvPointSearcher.requestFocus()
        KeyboardUtils.showKeyboardImplicit(tvPointSearcher)
        setAdapter()
        initPointSearcher()
    }

    override fun subscribe() {
        when (pointType) {
            PointTypeModel.START_POINT -> {
                viewModel.currentStartPoint.observe {
                    tvPointSearcher.setText(
                        context?.getString(
                            R.string.point_text,
                            it.name,
                            it.location
                        )
                    )
                }
            }
            PointTypeModel.END_POINT -> {
                viewModel.currentEndPoint.observe {
                    tvPointSearcher.setText(
                        context?.getString(
                            R.string.point_text,
                            it.name,
                            it.location
                        )
                    )
                }
            }
        }
    }

    override fun initClickListeners() {

    }

    private fun setAdapter() {
        rvPoints.layoutManager = LinearLayoutManager(context)
        adapter = PointsAdapter(::onItemClick)
        rvPoints.adapter = adapter
    }

    private fun initPointSearcher() {
        when(pointType){
            PointTypeModel.START_POINT -> {
                //
            }
            PointTypeModel.END_POINT -> {
                myLocation.visibility = View.GONE
            }
        }

        tvPointSearcher.setOnSearchCallback(object : SearchSuggestionsCallback {

            override fun onSuggestions(
                suggestions: List<SearchSuggestion>,
                responseInfo: ResponseInfo
            ) { adapter?.updateData(suggestions) }

            override fun onError(e: Exception) {}
        })
    }

    private fun onItemClick(data: SearchSuggestion) {
        searchEngine.select(data, selectionCallback)
    }

    private val selectionCallback = object : SearchSelectionCallback {
        override fun onCategoryResult(
            suggestion: SearchSuggestion,
            results: List<SearchResult>,
            responseInfo: ResponseInfo
        ) {
            TODO("Not yet implemented")
        }

        override fun onError(e: Exception) {
            TODO("Not yet implemented")
        }

        override fun onResult(
            suggestion: SearchSuggestion,
            result: SearchResult,
            responseInfo: ResponseInfo
        ) {
            val point = PointModel(
                suggestion.name,
                suggestion.address?.locality ?: "",
                result.coordinate?.latitude() ?: 0.0,
                result.coordinate?.longitude() ?: 0.0
            )
            when(pointType){
                PointTypeModel.START_POINT -> {
                    viewModel.setStartPoint(point)
                }
                PointTypeModel.END_POINT -> {
                    viewModel.setEndPoint(point)
                }
            }
        }

        override fun onSuggestions(
            suggestions: List<SearchSuggestion>,
            responseInfo: ResponseInfo
        ) {
            TODO("Not yet implemented")
        }

    }
}