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
    public fun getCharacters(@SuppressWarnings("SameParameterValue") @Nullable @Query("orderBy") modified: String,
                             @Query("ts") ts: String,
                             @Query("apikey") apiKey: String,
                             @Query("hash") hash: String,
                             @Nullable @Query("offset") offset: Int,
                             @Query("limit") limit: Int): LiveData<ApiResponse<CharacterResponse>>

    @GET("/v1/public/series/{seriesId}")
    public fun getSeriesBySeriesId(@Path("seriesId") id: String,
                                   @Query("apikey") apiKey: String,
                                   @Query("hash") hash: String,
                                   @Query("ts") ts: String)
            : LiveData<ApiResponse<DetailResponse>>


    @GET("/v1/public/characters/{id}")
    public fun getCharacterDetail(@Path("id") id: String,
                                  @Query("ts") ts: String,
                                  @Query("apikey") apiKey: String,
                                  @Query("hash") hash: String)
            : LiveData<ApiResponse<CharacterResponse>>

    @GET("/v1/public/characters/{characterId}/comics")
    public fun getComicsByCharacterId(@Path("characterId") characterId: String,
                                      @Query("apikey") apiKey: String,
                                      @Query("hash") hash: String,
                                      @Query("ts") ts: String,
                                      @Query("orderBy") orderBy: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/characters/{characterId}/series")
    public fun getSeriesByCharacterId(@Path("characterId") characterId: String,
                                      @Query("apikey") apiKey: String,
                                      @Query("hash") hash: String,
                                      @Query("ts") ts: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/characters/{characterId}/series")
    public fun getSeriesByCharacterId(@Path("characterId") characterId: String,
                                      @Query("apikey") apiKey: String,
                                      @Query("hash") hash: String,
                                      @Query("ts") ts: String,
                                      @Nullable @Query("offset") offset: Int,
                                      @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/characters/{characterId}/stories")
    public fun getStoriesByCharacterId(@Path("characterId") characterId: String,
                                       @Query("apikey") apiKey: String,
                                       @Query("hash") hash: String,
                                       @Query("ts") ts: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/characters/{characterId}/events")
    public fun getEventsByCharacterId(@Path("characterId") characterId: String,
                                      @Query("apikey") apiKey: String,
                                      @Query("hash") hash: String,
                                      @Query("ts") ts: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/series/{seriesId}/comics")
    public fun getComicsBySeriesId(@Path("seriesId") seriesId: String,
                                   @Query("apikey") apiKey: String,
                                   @Query("hash") hash: String,
                                   @Query("ts") ts: String,
                                   @Query("orderBy") orderBy: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/series/{seriesId}/comics")
    public fun getComicsBySeriesId(@Path("seriesId") seriesId: String,
                                   @Query("apikey") apiKey: String,
                                   @Query("hash") hash: String,
                                   @Query("ts") ts: String,
                                   @Query("orderBy") orderBy: String,
                                   @Nullable @Query("offset") offset: Int,
                                   @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/creators/{creatorId}/comics")
    public fun getComicsByCreatorId(@Path("creatorId") creatorId: String,
                                    @Query("apikey") apiKey: String,
                                    @Query("hash") hash: String,
                                    @Query("ts") ts: String,
                                    @Query("noVariants") noVariants: Boolean,
                                    @Query("orderBy") orderBy: String,
                                    @Nullable @Query("offset") offset: Int,
                                    @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>


    @GET("/v1/public/characters/{characterId}/comics")
    public fun getComicsByCharacterId(@Path("characterId") characterId: String,
                                      @Query("apikey") apiKey: String,
                                      @Query("hash") hash: String,
                                      @Query("ts") ts: String,
                                      @Query("orderBy") orderBy: String,
                                      @Nullable @Query("offset") offset: Int,
                                      @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/creators/{creatorId}/series")
    public fun getSeriesByCreatorId(@Path("creatorId") creatorId: String,
                                    @Query("apikey") apiKey: String,
                                    @Query("hash") hash: String,
                                    @Query("ts") ts: String,
                                    @Query("orderBy") orderBy: String,
                                    @Nullable @Query("offset") offset: Int,
                                    @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/creators/{creatorId}/events")
    public fun getEventsByCreatorId(@Path("creatorId") creatorId: String,
                                    @Query("apikey") apiKey: String,
                                    @Query("hash") hash: String,
                                    @Query("ts") ts: String,
                                    @Query("orderBy") orderBy: String,
                                    @Nullable @Query("offset") offset: Int,
                                    @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>


    @GET("v1/public/comics")
    public fun getComicsOfTheWeek(@Query("dateDescriptor") dateDescriptor: String,
                                  @Query("orderBy") orderBy: String,
                                  @Query("ts") ts: String,
                                  @Query("apikey") apiKey: String,
                                  @Query("hash") hash: String,
                                  @Nullable @Query("offset") offset: Int,
                                  @Query("limit") limit: Int)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/characters")
    public fun searchCharacterNameStartsWith(@Query("nameStartsWith") nameStartsWith: String,
                                             @Query("apikey") apiKey: String,
                                             @Query("hash") hash: String,
                                             @Query("ts") ts: String,
                                             @Query("orderBy") orderBy: String,
                                             @Nullable @Query("offset") offset: Int,
                                             @Query("limit") limit: Int): LiveData<ApiResponse<CharacterResponse>>


    @GET("/v1/public/comics")
    public fun searchComicsNameStartsWith(@Query("titleStartsWith") titleStartsWith: String,
                                          @Query("apikey") apiKey: String,
                                          @Query("hash") hash: String,
                                          @Query("ts") ts: String,
                                          @Query("orderBy") orderBy: String,
                                          @Nullable @Query("offset") offset: Int,
                                          @Query("limit") limit: Int): LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/series")
    public fun searchSeriesNameStartsWith(@Query("titleStartsWith") titleStartsWith: String,
                                          @Query("apikey") apiKey: String,
                                          @Query("hash") hash: String,
                                          @Query("ts") ts: String,
                                          @Query("orderBy") orderBy: String,
                                          @Nullable @Query("offset") offset: Int,
                                          @Query("limit") limit: Int): LiveData<ApiResponse<DetailResponse>>


    @GET("/v1/public/creators")
    public fun searchCreatorByName(@Query("nameStartsWith") nameStartsWith: String,
                                   @Query("apikey") apiKey: String,
                                   @Query("hash") hash: String,
                                   @Query("ts") ts: String,
                                   @Query("orderBy") orderBy: String,
                                   @Nullable @Query("offset") offset: Int,
                                   @Query("limit") limit: Int): LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/series/{seriesId}")
    public fun getSeriesDetailById(@Path("seriesId") seriesId: String,
                                   @Query("apikey") apiKey: String,
                                   @Query("hash") hash: String,
                                   @Query("ts") ts: String): LiveData<ApiResponse<DetailResponse>>
}