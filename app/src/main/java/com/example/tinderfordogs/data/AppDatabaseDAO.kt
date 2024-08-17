package com.example.tinderfordogs.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tinderfordogs.model.TinderCardModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDatabaseDAO {

    @Query("SELECT * FROM pets_tbl")
    fun getAllDogs(): Flow<List<TinderCardModel>>

    @Delete
    suspend fun deleteNote(dog:TinderCardModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dog: TinderCardModel)
}

