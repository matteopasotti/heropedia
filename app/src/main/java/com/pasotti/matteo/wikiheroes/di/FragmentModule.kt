package com.pasotti.matteo.wikiheroes.di

import com.pasotti.matteo.wikiheroes.view.ui.detail.FragmentCharacterDetail
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.FragmentDetailItem
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailImageFragment
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics.MoreGalleryFragment
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_info.MoreInfoFragment
import com.pasotti.matteo.wikiheroes.view.ui.gallery.HorizontalGalleryFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.FragmentHome
import com.pasotti.matteo.wikiheroes.view.ui.home.characters.HomeCharactersFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.comics.HomeComicsFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.desk.HomeDeskFragment
import com.pasotti.matteo.wikiheroes.view.ui.person.comics.CreatorComicsFragment
import com.pasotti.matteo.wikiheroes.view.ui.person.series.CreatorSeriesFragment
import com.pasotti.matteo.wikiheroes.view.ui.search.FragmentSearchScreen
import com.pasotti.matteo.wikiheroes.view.ui.splash.FragmentSplashScreen
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    /**
     * Here we attach our Fragments to Dagger Graph
     */

    @ContributesAndroidInjector
    internal abstract fun contributeHorizontalGalleryFragment(): HorizontalGalleryFragment

    @ContributesAndroidInjector
    internal abstract fun contributeMoreGalleryFragment(): MoreGalleryFragment

    @ContributesAndroidInjector
    internal abstract fun contributeMoreInfoFragment(): MoreInfoFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeCharactersFragment() : HomeCharactersFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeComicsFragment() : HomeComicsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeDetailImageFragment() : DetailImageFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeDeskFragment() : HomeDeskFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCreatorComicsFragment() : CreatorComicsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCreatorSeriesFragment() : CreatorSeriesFragment

    @ContributesAndroidInjector
    internal abstract fun contributeFragmentSplashScreen() : FragmentSplashScreen

    @ContributesAndroidInjector
    internal abstract fun contributeFragmentHome() : FragmentHome

    @ContributesAndroidInjector
    internal abstract fun contributeFragmentSearchScreen() : FragmentSearchScreen
}