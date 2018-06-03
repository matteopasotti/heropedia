package com.pasotti.matteo.wikiheroes.di

import android.app.Application
import android.arch.persistence.room.Room
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.room.AppDatabase
import com.pasotti.matteo.wikiheroes.room.CharacterDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com")
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

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