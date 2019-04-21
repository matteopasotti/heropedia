package com.pasotti.matteo.wikiheroes.di

import com.pasotti.matteo.wikiheroes.view.ui.home.HomeActivity
import com.pasotti.matteo.wikiheroes.view.ui.creator.CreatorDetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicActivity
import com.pasotti.matteo.wikiheroes.view.ui.home.splash.SplashActivity
import com.pasotti.matteo.wikiheroes.view.ui.search.SearchActivity
import com.pasotti.matteo.wikiheroes.view.ui.seeall.SeeAllActivity
import com.pasotti.matteo.wikiheroes.view.ui.seeall.series.SeriesSeeAllActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    /**
     * Here we attach our Activities to Dagger Graph
     */

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDeatilComicActivity(): DetailComicActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSeeAllActivity() : SeeAllActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSeriesSeeAllActivity() : SeriesSeeAllActivity

    @ContributesAndroidInjector
    internal abstract fun contributeCreatorActivity(): CreatorDetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSplashActivity() : SplashActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSearchActivity() : SearchActivity


}