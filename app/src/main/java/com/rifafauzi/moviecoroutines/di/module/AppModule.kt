package com.rifafauzi.moviecoroutines.di.module

import android.app.Application
import android.content.Context
import com.rifafauzi.moviecoroutines.MovieApp
import com.rifafauzi.moviecoroutines.api.ApiService
import com.rifafauzi.moviecoroutines.repository.MovieRepository
import com.rifafauzi.moviecoroutines.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */
 
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: MovieApp) : Context = app

    @Provides
    @Singleton
    fun provideApplocation(app: MovieApp) : Application = app

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    @Named("IO")
    fun provideBackgroundDispatchers(): CoroutineDispatcher =
        Dispatchers.IO
    @Provides
    @Singleton
    @Named("MAIN")
    fun provideMainDispatchers(): CoroutineDispatcher =
        Dispatchers.Main

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: ApiService): MovieRepository=
        MovieRepositoryImpl(apiService)
}