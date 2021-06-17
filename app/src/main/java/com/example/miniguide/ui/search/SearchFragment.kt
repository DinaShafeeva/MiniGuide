package com.example.miniguide.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniguide.R
import com.example.miniguide.data.PointModel
import com.example.miniguide.data.PointTypeModel
import com.example.miniguide.helper.Constatns.KEY_POINT_TYPE
import com.example.miniguide.helper.KeyboardUtils
import com.example.miniguide.ui.base.BaseFragment
import com.mapbox.search.*
import com.mapbox.search.result.SearchResult
import com.mapbox.search.result.SearchSuggestion
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel>() {
    private val searchEngine = MapboxSearchSdk.createSearchEngine()

    private val viewModel: SearchViewModel by viewModels()

    companion object {
        fun getBundle(pointTypeModel: PointTypeModel) = Bundle().apply {
            putParcelable(KEY_POINT_TYPE, pointTypeModel)
        }
    }

    private var adapter: PointsAdapter? = null

    private var pointType: PointTypeModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pointType = requireArguments()[KEY_POINT_TYPE] as PointTypeModel
    }

    override fun initViews() {
        tvPointSearcher.requestFocus()
        KeyboardUtils.showKeyboardImplicit(tvPointSearcher)
        setAdapter()
        initPointSearcher()
        //viewModel = SearchViewModel()
//        viewModel.view = view
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
            }
            PointTypeModel.END_POINT -> {
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
        //override lateinit var viewModel: SearchViewModel
}