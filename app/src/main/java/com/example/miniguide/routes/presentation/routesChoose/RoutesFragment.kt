package com.example.miniguide.routes.presentation.routesChoose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miniguide.R
import com.example.miniguide.app.di.Injector
import com.example.miniguide.common.base.BaseFragment
import com.example.miniguide.routes.presentation.pointSearch.model.PointTypeModel
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.fragment_routes.*

class RoutesFragment : BaseFragment<RoutesViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }

    override fun inject() {
        Injector.plusRoutesComponent(this).inject(this)
    }

    override fun subscribe() {
        viewModel.currentStartPoint.observe {
            tvStartPoint.text = context?.getString(R.string.point_text, it.name, it.location)
        }
        viewModel.currentEndPoint.observe {
            tvEndPoint.text = context?.getString(R.string.point_text, it.name, it.location)
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
            viewModel.onCreateRouteClick()
        }
        activity?.resetBtn?.setOnClickListener { viewModel.back() }
    }

    override fun initViews() {
        //
    }
}