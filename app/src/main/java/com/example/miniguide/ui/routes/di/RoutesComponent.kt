package com.example.miniguide.ui.routes.di

import androidx.fragment.app.Fragment
import com.example.miniguide.ui.routes.RoutesFragment
import com.example.miniguide.ui.routes.di.scope.RoutesScope
import dagger.BindsInstance
import dagger.Subcomponent

@RoutesScope
@Subcomponent(modules = [RoutesModule::class])
interface RoutesComponent {

    fun inject(routesFragment: RoutesFragment)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun withFragment(fragment: Fragment): Builder

        fun build(): RoutesComponent

    }
}