package com.pasotti.matteo.wikiheroes.di

import com.pasotti.matteo.wikiheroes.view.ui.HomeActivity
import com.pasotti.matteo.wikiheroes.view.ui.creator.CreatorDetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicActivity
import com.pasotti.matteo.wikiheroes.view.ui.home.login.LoginActivity
import com.pasotti.matteo.wikiheroes.view.ui.seeall.SeeAllActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributeLoginActivity(): LoginActivity


    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDeatilComicActivity(): DetailComicActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSeeAllActivity() : SeeAllActivity

    @ContributesAndroidInjector
    internal abstract fun contributeCreatorActivity(): CreatorDetailActivity


}