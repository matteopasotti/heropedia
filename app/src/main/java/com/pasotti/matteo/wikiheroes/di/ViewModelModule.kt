package com.pasotti.matteo.wikiheroes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.view.ui.HomeActivityViewModel
import com.pasotti.matteo.wikiheroes.view.ui.creator.CreatorDetailViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivityViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics.MoreGalleryFragmentViewModel
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_info.MoreInfoViewModel
import com.pasotti.matteo.wikiheroes.view.ui.gallery.HorizontalGalleryViewModel
import com.pasotti.matteo.wikiheroes.view.ui.seeall.SeeAllViewModel
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
    @ViewModelKey(DetailActivityViewModel::class)
    internal abstract fun bindDetailActivityViewModel(detailActivityViewModel: DetailActivityViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HorizontalGalleryViewModel::class)
    internal abstract fun bindHorizontalGalleryViewModel(horizontalGalleryViewModel: HorizontalGalleryViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreGalleryFragmentViewModel::class)
    internal abstract fun bindMoreGalleryFragmentViewModel(moreGalleryFragmentViewModel: MoreGalleryFragmentViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreInfoViewModel::class)
    internal abstract fun bindMoreInfoViewModel(moreInfoViewModel: MoreInfoViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SeeAllViewModel::class)
    internal abstract fun bindSeeAllViewModel( seeAllViewModel: SeeAllViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailComicViewModel::class)
    internal abstract fun bindDetailComicViewModel( detailComicViewModel: DetailComicViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatorDetailViewModel::class)
    internal abstract fun bindCreatorDetailViewModel( creatorDetailViewModel: CreatorDetailViewModel) : ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory) : ViewModelProvider.Factory
}