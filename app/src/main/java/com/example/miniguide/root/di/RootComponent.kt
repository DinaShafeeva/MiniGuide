package com.example.miniguide.root.di

import com.example.miniguide.root.RootActivity
import com.example.miniguide.common.di.scope.ScreenScope
import dagger.Subcomponent

@ScreenScope
@Subcomponent
interface RootComponent {

    fun inject(rootActivity: RootActivity)

    @Subcomponent.Builder
    interface Builder {

        fun build(): RootComponent
    }
}