package com.pasotti.matteo.wikiheroes.di

import com.pasotti.matteo.wikiheroes.view.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity
}