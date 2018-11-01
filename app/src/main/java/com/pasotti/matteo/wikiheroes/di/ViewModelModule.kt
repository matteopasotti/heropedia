package com.pasotti.matteo.wikiheroes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.view.ui.HomeActivityViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(homeActivityViewModel: HomeActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailActivityViewModel:: class)
    internal abstract fun bindDetailActivityViewModel(detailActivityViewModel: DetailActivityViewModel) : ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory) : ViewModelProvider.Factory
}