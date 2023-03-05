package com.example.giphytask.data.cloud

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * сервис для получения гифок
 */
interface GifsService {

    @GET("trending")
    suspend fun fetchTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): ResponseBody

    @GET("search")
    suspend fun fetchSearchedGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("q") searchedWord: String
    ): ResponseBody
}