package com.example.miniguide.common.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import javax.inject.Inject

abstract class BaseFragment<T : ViewModel> : Fragment() {

    @Inject
    protected open lateinit var viewModel: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initClickListeners()
    }

    abstract fun inject()

    abstract fun initClickListeners()

}