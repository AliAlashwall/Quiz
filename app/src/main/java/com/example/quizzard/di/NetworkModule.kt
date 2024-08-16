package com.example.quizzard.di

import com.example.quizzard.data.data_source.remote.data_model.QuizApiService
import com.example.quizzard.data.repository.QuizRepositoryImpl
import com.example.quizzard.domain.repository.QuizRepository
import com.example.quizzard.domain.use_case.ComputerQuestionsUseCase
import com.example.quizzard.domain.use_case.DailyQuizUseCase
import com.example.quizzard.domain.use_case.HistoryQuestionsUseCase
import com.example.quizzard.domain.use_case.MathQuestionsUseCase
import com.example.quizzard.domain.use_case.SportsQuestionUseCase
import com.example.quizzard.domain.use_case.QuizUseCase
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
    fun provideQuizRepository(quizApiService: QuizApiService): QuizRepository {
        return QuizRepositoryImpl(quizApiService)
    }

    @Provides
    @Singleton
    fun provideSubjectQuestionUseCase(quizRepository: QuizRepository): QuizUseCase {
        return QuizUseCase(
            mathQuestionsUseCase = MathQuestionsUseCase(quizRepository),
            computerQuestionsUseCase = ComputerQuestionsUseCase(quizRepository),
            sportsQuestionUseCase = SportsQuestionUseCase(quizRepository),
            historyQuestionsUseCase = HistoryQuestionsUseCase(quizRepository),
            dailyQuizUseCase = DailyQuizUseCase(quizRepository),
        )

    }
}