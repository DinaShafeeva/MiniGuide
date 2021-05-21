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
import com.mapbox.search.ResponseInfo
import com.mapbox.search.SearchSelectionCallback
import com.mapbox.search.result.SearchResult
import com.mapbox.search.result.SearchSuggestion
import kotlinx.android.synthetic.main.fragment_point_search.*
import javax.inject.Inject

private const val KEY_POINT_TYPE = "KEY_POINT_TYPE"

class PointSearchFragment : BaseFragment<PointSearchViewModel>() {

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
                    tvPointSearcher.setText(context?.getString(R.string.point_text, it.name, it.location))
                }
            }
            PointTypeModel.END_POINT -> {
                viewModel.currentEndPoint.observe {
                    tvPointSearcher.setText(context?.getString(R.string.point_text, it.name, it.location))
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

        tvPointSearcher.setOnSearchCallback(object : SearchSelectionCallback {

            override fun onSuggestions(
                suggestions: List<SearchSuggestion>,
                responseInfo: ResponseInfo
            ) {
                val points = mutableListOf<PointModel>()
                suggestions.forEach {
                    points.add(
                        PointModel(
                            it.name,
                            it.address?.locality ?: ""
                        )
                    )
                }
                adapter?.updateData(points)
            }

            override fun onResult(
                suggestion: SearchSuggestion,
                result: SearchResult,
                responseInfo: ResponseInfo
            ) {
            }

            override fun onCategoryResult(
                suggestion: SearchSuggestion,
                results: List<SearchResult>,
                responseInfo: ResponseInfo
            ) {
            }

            override fun onError(e: Exception) {}
        })
    }

    private fun onItemClick(data: PointModel) {
        when(pointType){
            PointTypeModel.START_POINT -> {
                viewModel.setStartPoint(data)
            }
            PointTypeModel.END_POINT -> {
                viewModel.setEndPoint(data)
            }
        }
    }
}