package com.example.tinderfordogs.network

import com.example.tinderfordogs.model.TinderCardModel
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface FetchDogApi {

    @GET("random")
    suspend fun getAllDogs(): TinderCardModel

}