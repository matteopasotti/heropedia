package com.pasotti.matteo.wikiheroes.di

import android.arch.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.view.HomeActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(homeActivityViewModel: HomeActivityViewModel): ViewModel
}