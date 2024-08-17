package com.example.tinderfordogs.repository

import com.example.tinderfordogs.data.AppDatabase
import com.example.tinderfordogs.model.TinderCardModel
import com.example.tinderfordogs.network.FetchDogApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchDogRepository @Inject constructor(private val api:FetchDogApi,private val appDatabase:AppDatabase) {

    suspend fun getAllDogs():TinderCardModel{

        return api.getAllDogs()
    }


    suspend fun addDog(dog: TinderCardModel)=appDatabase.appDao().insert(dog)






}