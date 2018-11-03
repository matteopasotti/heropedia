package com.pasotti.matteo.wikiheroes.api

import androidx.lifecycle.LiveData
import androidx.annotation.Nullable
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import io.reactivex.Single
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


    @GET("/v1/public/characters/{id}")
    public fun getCharacterDetail(@Path("id") id: String,
                                  @Query("ts") ts: String,
                                  @Query("apikey") apiKey: String,
                                  @Query("hash") hash: String)
            : LiveData<ApiResponse<CharacterResponse>>


    @GET("/v1/public/{type}/{id}")
    public fun getDetail(@Path("type") type: String,
                         @Path("id") id: String,
                         @Query("ts") ts: String,
                         @Query("apikey") apiKey: String,
                         @Query("hash") hash: String)
            : Single<DetailResponse>

    @GET("/v1/public/characters/{characterId}/comics")
    public fun getComicsByCharacterId(@Path("characterId") characterId: String,
                                      @Query("apikey") apiKey: String,
                                      @Query("hash") hash: String,
                                      @Query("ts") ts: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/characters/{characterId}/series")
    public fun getSeriesByCharacterId(@Path("characterId") characterId: String,
                                      @Query("apikey") apiKey: String,
                                      @Query("hash") hash: String,
                                      @Query("ts") ts: String)
            : LiveData<ApiResponse<DetailResponse>>

    @GET("/v1/public/characters/{characterId}/stories")
    public fun getStoriesByCharacterId(@Path("characterId") characterId: String,
                                      @Query("apikey") apiKey: String,
                                      @Query("hash") hash: String,
                                      @Query("ts") ts: String)
            : LiveData<ApiResponse<DetailResponse>>
}