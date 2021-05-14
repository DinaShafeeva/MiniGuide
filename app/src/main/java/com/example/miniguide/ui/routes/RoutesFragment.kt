package com.example.miniguide.ui.routes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miniguide.R
import com.example.miniguide.ui.base.BaseFragment


class RoutesFragment : BaseFragment<RoutesViewModel>() {
    override lateinit var viewModel: RoutesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }

    override fun initClickListeners() {
        //
    }


}