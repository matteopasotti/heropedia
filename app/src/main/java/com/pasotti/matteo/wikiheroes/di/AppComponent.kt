package com.pasotti.matteo.wikiheroes.di

import android.app.Application
import com.pasotti.matteo.wikiheroes.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (ActivityModule::class),
    (FragmentModule::class),
    (ViewModelModule::class),
    (AppModule::class),
    (NetModule::class)])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder
        @BindsInstance
        fun baseUrl(url : String) : AppComponent.Builder
        fun build(): AppComponent
    }

    fun inject(instance: MyApplication)
}