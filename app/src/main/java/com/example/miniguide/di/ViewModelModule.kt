package com.example.miniguide.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniguide.app.AppComponent
import com.example.miniguide.domain.routes.RoutesInteractor
import com.example.miniguide.ui.base.ViewModelFactory
import com.example.miniguide.ui.routes.RoutesViewModel
import com.example.miniguide.ui.search.SearchViewModel
import dagger.*
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ViewModelModule {
//    @Binds
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(RoutesViewModel::class)
//    abstract fun bindRoutesViewModel(viewModel: RoutesViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchViewModel::class)
//    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel


    @Singleton
    @Provides
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun provideSearchViewModel(
        routesInteractor: RoutesInteractor
    ): ViewModel {
        return SearchViewModel(routesInteractor)
    }


    @Singleton
    @Provides
    fun provideSearcherViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): SearchViewModel =
        ViewModelProvider(fragment, viewModelFactory).get(SearchViewModel::class.java)



}
