package com.pasotti.matteo.wikiheroes

import com.pasotti.matteo.wikiheroes.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication(){

    private val appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()


    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}