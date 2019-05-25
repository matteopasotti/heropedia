package com.pasotti.matteo.wikiheroes

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.pasotti.matteo.wikiheroes.di.AppComponent
import com.pasotti.matteo.wikiheroes.di.DaggerAppComponent
import com.pasotti.matteo.wikiheroes.utils.Utils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        DaggerAppComponent.builder()
                .application(this)
                .baseUrl(Utils.BASE_URL)
                .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment> = fragmentInjector


}