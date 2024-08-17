package com.example.tinderfordogs.repository

import com.example.tinderfordogs.data.AppDatabase
import com.example.tinderfordogs.model.TinderCardModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoriteDogsRepository @Inject constructor(private val appDatabase: AppDatabase){



    suspend fun deleteDog(dog: TinderCardModel)=appDatabase.appDao().deleteNote(dog)

    suspend fun getAllDogs(): Flow<List<TinderCardModel>> = appDatabase.appDao().getAllDogs().flowOn(
        Dispatchers.IO).conflate()
}