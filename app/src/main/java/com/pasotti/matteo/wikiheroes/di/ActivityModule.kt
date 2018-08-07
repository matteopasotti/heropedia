package com.pasotti.matteo.wikiheroes.di

import com.pasotti.matteo.wikiheroes.view.ui.HomeActivity
import com.pasotti.matteo.wikiheroes.view.ui.home.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributeLoginActivity(): LoginActivity
}