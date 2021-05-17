package com.example.miniguide.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

abstract class  BaseFragment<T : ViewModel> : Fragment() {

    protected abstract var viewModel: T

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribe()
        initClickListeners()
        initViews()
    }

    abstract fun initClickListeners()

    abstract fun initViews()

    abstract fun subscribe()

    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProvider(this, viewModelFactory).get(T::class.java)
        vm.body()
        return vm
    }
    inline fun <V> Flow<V>.observe(crossinline collector: suspend (V) -> Unit) {
        lifecycleScope.launchWhenResumed {
            collect(collector)
        }
    }
}