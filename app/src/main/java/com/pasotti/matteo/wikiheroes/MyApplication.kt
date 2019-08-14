package com.pasotti.matteo.wikiheroes

import android.app.Application
import com.pasotti.matteo.wikiheroes.di.dbModule
import com.pasotti.matteo.wikiheroes.di.mainModule
import com.pasotti.matteo.wikiheroes.di.netModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin(this , listOf(mainModule, netModule, dbModule))

    }

}




