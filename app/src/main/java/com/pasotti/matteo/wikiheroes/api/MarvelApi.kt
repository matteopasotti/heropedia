package com.pasotti.matteo.wikiheroes.api

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    fun getCharacters(@SuppressWarnings("SameParameterValue") @Nullable @Query("orderBy") modified: String,
                      @Query("ts") ts: String,
                      @Query("apikey") apiKey: String,
                      @Query("hash") hash: String,
                      @Nullable @Query("offset") offset: Int,
                      @Query("limit") limit: Int): LiveData<ApiResponse<CharacterResponse>>


    @GET("/characters")
    fun getCharacters(@Query("orderBy") modified: String,
                      @Nullable @Query("offset") offset: Int,
                      @Query("limit") limit: Int): LiveData<ApiResponse<CharacterResponse>>

    @GET("/series/{seriesId}")
    fun getSeriesBySeriesId(@Path("seriesId") id: String)
            : LiveData<ApiResponse<DetailResponse>>


    @GET("/v1/public/characters/{id}")
    fun getCharacterDetail(@Path("id") id: String,
                           @Query("ts") ts: String,
                           @Query("apikey") apiKey: String,
                           @Query("hash") hash: String)
            : LiveData<ApiResponse<CharacterResponse>>

    @GET("/characters/{characterId}/comics")
    fun getComicsByCharacterId(@Path("characterId") characterId: String,
                               @Query("orderBy") orderBy: String,
                               @Query("limit") limit : Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/characters/{characterId}/series")
    fun getSeriesByCharacterId(@Path("characterId") characterId: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/characters/{characterId}/series")
    fun getSeriesByCharacterId(@Path("characterId") characterId: String,
                               @Nullable @Query("offset") offset: Int,
                               @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/characters/{characterId}/stories")
    fun getStoriesByCharacterId(@Path("characterId") characterId: String )
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/characters/{characterId}/events")
    fun getEventsByCharacterId(@Path("characterId") characterId: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/series/{seriesId}/comics")
    fun getComicsBySeriesId(@Path("seriesId") seriesId: String,
                            @Query("orderBy") orderBy: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/series/{seriesId}/comics")
    fun getComicsBySeriesId(@Path("seriesId") seriesId: String,
                            @Query("orderBy") orderBy: String,
                            @Nullable @Query("offset") offset: Int,
                            @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/creators/{creatorId}/comics")
    fun getComicsByCreatorId(@Path("creatorId") creatorId: String,
                             @Query("noVariants") noVariants: Boolean,
                             @Query("orderBy") orderBy: String,
                             @Nullable @Query("offset") offset: Int,
                             @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>


    @GET("/characters/{characterId}/comics")
    fun getComicsByCharacterId(@Path("characterId") characterId: String,
                               @Query("orderBy") orderBy: String,
                               @Nullable @Query("offset") offset: Int,
                               @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/creators/{creatorId}/series")
    fun getSeriesByCreatorId(@Path("creatorId") creatorId: String,
                             @Query("orderBy") orderBy: String,
                             @Nullable @Query("offset") offset: Int,
                             @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/creators/{creatorId}/events")
    fun getEventsByCreatorId(@Path("creatorId") creatorId: String,
                             @Query("orderBy") orderBy: String,
                             @Nullable @Query("offset") offset: Int,
                             @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/comics")
    fun getComicsOfTheWeek(@Query("dateDescriptor") dateDescriptor: String,
                           @Query("orderBy") orderBy: String,
                           @Nullable @Query("offset") offset: Int,
                           @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/characters")
    fun searchCharacterNameStartsWith(@Query("nameStartsWith") nameStartsWith: String,
                                      @Query("orderBy") orderBy: String,
                                      @Nullable @Query("offset") offset: Int,
                                      @Query("limit") limit: Int): LiveData<ApiResponse<CharacterResponse>>


    @GET("/comics")
    fun searchComicsNameStartsWith(@Query("titleStartsWith") titleStartsWith: String,
                                   @Query("orderBy") orderBy: String,
                                   @Nullable @Query("offset") offset: Int,
                                   @Query("limit") limit: Int): LiveData<ApiResponse<DetailResponse>>

    @GET("/series")
    fun searchSeriesNameStartsWith(@Query("titleStartsWith") titleStartsWith: String,
                                   @Query("orderBy") orderBy: String,
                                   @Nullable @Query("offset") offset: Int,
                                   @Query("limit") limit: Int): LiveData<ApiResponse<DetailResponse>>


    @GET("/creators")
    fun searchCreatorByName(@Query("nameStartsWith") nameStartsWith: String,
                            @Query("orderBy") orderBy: String,
                            @Nullable @Query("offset") offset: Int,
                            @Query("limit") limit: Int): LiveData<ApiResponse<DetailResponse>>

    @GET("/creators/{creatorId}")
    fun getCreatorDetailById(@Path("creatorId") creatorId: String): LiveData<ApiResponse<DetailResponse>>

    @GET("/series/{seriesId}")
    fun getSeriesDetailById(@Path("seriesId") seriesId: String): LiveData<ApiResponse<DetailResponse>>
}