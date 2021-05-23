package com.example.miniguide.map.di

import androidx.fragment.app.Fragment
import com.example.miniguide.common.di.scope.ScreenScope
import com.example.miniguide.common.di.viewModel.ViewModelModule
import com.example.miniguide.map.presentation.MapFragment
import dagger.BindsInstance
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [MapModule::class, ViewModelModule::class,])
interface MapComponent {

    fun inject(mapFragment: MapFragment)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun withFragment(fragment: Fragment): Builder

        fun build(): MapComponent

    }
}