package com.pasotti.matteo.wikiheroes.di

import android.app.Application
import androidx.room.Room
import com.pasotti.matteo.wikiheroes.api.LiveDataCallAdapterFactory
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.api.SchedulersFacade
import com.pasotti.matteo.wikiheroes.room.AppDatabase
import com.pasotti.matteo.wikiheroes.room.CharacterDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module
class AppModule {


    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "ComicCatalogDB.db").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): CharacterDao {
        return database.characterDao()
    }
}