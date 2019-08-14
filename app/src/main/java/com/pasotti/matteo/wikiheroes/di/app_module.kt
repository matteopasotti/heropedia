package com.pasotti.matteo.wikiheroes.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.pasotti.matteo.wikiheroes.repository.*
import com.pasotti.matteo.wikiheroes.view.ui.creator.CreatorDetailViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivityViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailImageViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailItemViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics.MoreGalleryFragmentViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_info.MoreInfoViewModel
import com.pasotti.matteo.wikiheroes.view.ui.gallery.HorizontalGalleryViewModel
import com.pasotti.matteo.wikiheroes.view.ui.home.HomeActivityViewModel
import com.pasotti.matteo.wikiheroes.view.ui.home.comics.HomeComicsViewModel
import com.pasotti.matteo.wikiheroes.view.ui.home.desk.HomeDeskViewModel
import com.pasotti.matteo.wikiheroes.view.ui.person.PersonDetailActivityViewModel
import com.pasotti.matteo.wikiheroes.view.ui.person.comics.CreatorFragmentsViewModel
import com.pasotti.matteo.wikiheroes.view.ui.search.SearchActivityViewModel
import com.pasotti.matteo.wikiheroes.view.ui.seeall.SeeAllViewModel
import com.pasotti.matteo.wikiheroes.view.ui.seeall.series.SeriesSeeAllViewModel
import com.pasotti.matteo.wikiheroes.view.ui.splash.SplashActivityViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val mainModule = applicationContext {

    bean { providePreferences(androidApplication()) }

    bean { ComicsRepository(get(), get(), get(), get()) }

    bean { CharactersRepository(get(), get(), get(), get()) }

    bean { ComicsRepository(get(), get(), get(), get()) }

    bean { CreatorsRepository(get(),get(),get()) }

    bean { MainRepository(get()) }

    bean { SeriesRepository(get(), get()) }

    bean { com.pasotti.matteo.wikiheroes.utils.PreferenceManager(get())}

    viewModel { HomeActivityViewModel(get()) }

    viewModel { DetailActivityViewModel(get(), get()) }

    viewModel { HorizontalGalleryViewModel(get(), get(), get()) }

    viewModel { MoreGalleryFragmentViewModel(get(), get()) }

    viewModel { MoreInfoViewModel(get(), get()) }

    viewModel { SeriesSeeAllViewModel(get(), get(), get()) }

    viewModel { SeeAllViewModel(get(), get(), get()) }

    viewModel { DetailItemViewModel(get(), get()) }

    viewModel { CreatorDetailViewModel(get(), get()) }

    viewModel { HomeComicsViewModel(get()) }

    viewModel { SplashActivityViewModel(get(), get()) }

    viewModel { HomeDeskViewModel(get(), get(), get(), get()) }

    viewModel { DetailImageViewModel() }

    viewModel { SearchActivityViewModel(get(), get(), get(), get()) }

    viewModel { PersonDetailActivityViewModel(get()) }

    viewModel { CreatorFragmentsViewModel(get(), get()) }
}

fun providePreferences(application: Application) : SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(application)
}