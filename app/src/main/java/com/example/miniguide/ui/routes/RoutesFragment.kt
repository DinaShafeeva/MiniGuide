package com.example.miniguide.ui.routes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.miniguide.R
import com.example.miniguide.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_routes.*

@AndroidEntryPoint
class RoutesFragment : BaseFragment<RoutesViewModel>() {
//    override lateinit var viewModel: RoutesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }

//    override fun subscribe() {
//        viewModel.currentStartPoint.observe {
//            Log.e("currentStartPoint - ", it.toString())
//            tvStartPoint.text = context?.getString(R.string.point_text, it.name, it.location)
//        }
//        viewModel.currentEndPoint.observe {
//            Log.e("currentEndPoint - ", it.toString())
//            tvEndPoint.text = context?.getString(R.string.point_text, it.name, it.location)
//        }
//    }

    override fun initClickListeners() {
        tvStartPoint.setOnClickListener {
            Log.d("startPoint", "start")
            view?.findNavController()?.navigate(R.id.searchFragment)
        }
        tvEndPoint.setOnClickListener {
            view?.findNavController()?.navigate(R.id.searchFragment)
        }
    }

    override fun initViews() {
    }

    override fun subscribe() {
    }
}