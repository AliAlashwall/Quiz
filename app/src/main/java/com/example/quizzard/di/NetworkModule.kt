package com.example.quizzard.di

import com.example.quizzard.data.data_source.remote.data_model.QuizApiService
import com.example.quizzard.data.repository.RemoteQuizRepositoryImpl
import com.example.quizzard.domain.repository.RemoteQuizRepository
import com.example.quizzard.presentation.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
            connectTimeout(20, TimeUnit.MINUTES) // connect timeout
            readTimeout(30, TimeUnit.MINUTES) // socket timeout
            writeTimeout(20, TimeUnit.MINUTES) // write timeout
        }.build()
    }

    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): QuizApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(QuizApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(quizApiService: QuizApiService): RemoteQuizRepository {
        return RemoteQuizRepositoryImpl(quizApiService)
    }
}