package com.example.miniguide.routes.di

import com.example.miniguide.routes.pointSearch.PointSearchFragment
import androidx.fragment.app.Fragment
import com.example.miniguide.common.di.scope.ScreenScope
import com.example.miniguide.common.di.viewModel.ViewModelModule
import com.example.miniguide.routes.routesChoose.RoutesFragment
import dagger.BindsInstance
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [RoutesModule::class, ViewModelModule::class,])
interface RoutesComponent {

    fun inject(routesFragment: RoutesFragment)

    fun inject(pointSearch: PointSearchFragment)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun withFragment(fragment: Fragment): Builder

        fun build(): RoutesComponent

    }
}