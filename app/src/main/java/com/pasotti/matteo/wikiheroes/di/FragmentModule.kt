package com.pasotti.matteo.wikiheroes.di

import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailImageFragment
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics.MoreGalleryFragment
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_info.MoreInfoFragment
import com.pasotti.matteo.wikiheroes.view.ui.gallery.HorizontalGalleryFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.characters.HomeCharactersFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.comics.HomeComicsFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.desk.HomeDeskFragment
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
}