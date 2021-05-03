package com.example.miniguide.ui.routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miniguide.R
import com.example.miniguide.app.di.Injector
import com.example.miniguide.common.base.BaseFragment

class RoutesFragment : BaseFragment<RoutesViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.routes_fragment, container, false)
    }

    override fun inject() {
        Injector.plusRoutesComponent(this).inject(this)
    }

    override fun initClickListeners() {
        //
    }

}