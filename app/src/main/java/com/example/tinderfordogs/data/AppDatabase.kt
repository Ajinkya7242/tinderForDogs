package com.example.tinderfordogs.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tinderfordogs.model.TinderCardModel

@Database(entities = [TinderCardModel::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun appDao():AppDatabaseDAO

}