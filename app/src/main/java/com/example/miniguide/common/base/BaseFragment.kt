package com.example.miniguide.common.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

abstract class BaseFragment<T : ViewModel> : Fragment() {

    @Inject
    protected open lateinit var viewModel: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        initViews()
        initClickListeners()
    }

    abstract fun subscribe()

    abstract fun inject()

    abstract fun initViews()

    abstract fun initClickListeners()

    inline fun <V> Flow<V>.observe(crossinline collector: suspend (V) -> Unit) {
        lifecycleScope.launchWhenResumed {
            collect(collector)
        }
    }

}