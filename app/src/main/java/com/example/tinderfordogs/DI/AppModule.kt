package com.example.tinderfordogs.DI

import android.content.Context
import androidx.room.Room
import com.example.tinderfordogs.data.AppDatabase
import com.example.tinderfordogs.data.AppDatabaseDAO
import com.example.tinderfordogs.network.FetchDogApi
import com.example.tinderfordogs.repository.FavoriteDogsRepository
import com.example.tinderfordogs.repository.FetchDogRepository
import com.example.tinderfordogs.utils.Constants.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideApiService(): FetchDogApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchDogApi::class.java)
    }

    @Provides
    fun providesFavoriteDog(appDatabase:AppDatabase):FavoriteDogsRepository{
        return FavoriteDogsRepository(appDatabase)
    }


    @Singleton
    @Provides
    fun providesNotesDao(appDatabase:AppDatabase):AppDatabaseDAO=appDatabase.appDao()

    @Singleton
    @Provides
    fun provideAddDatabase(@ApplicationContext context: Context):AppDatabase
            = Room.databaseBuilder(context,AppDatabase::class.java
        ,"pets_tbl").fallbackToDestructiveMigrationFrom().build()


    @Provides
    fun provideDogRepository(apiService: FetchDogApi,appDatabase: AppDatabase): FetchDogRepository {
        return FetchDogRepository(apiService,appDatabase)
    }


}