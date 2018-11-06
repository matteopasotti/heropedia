package com.pasotti.matteo.wikiheroes.view.ui.detail_items

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

abstract class DetailItemBindingViewModel : BaseObservable() {

    @Bindable
    abstract fun getTitle(): String?

    @Bindable
    abstract fun getDescription(): String?

    @Bindable
    abstract fun getUrlImage(): String?


}