package com.example.quizzard.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizzard.data.data_source.local.Converters
import com.example.quizzard.data.data_source.local.UserDao
import com.example.quizzard.data.data_source.local.UserDatabase
import com.example.quizzard.data.repository.LocalQuizRepositoryImpl
import com.example.quizzard.domain.repository.LocalQuizRepository
import com.example.quizzard.domain.use_case.local.GetUserUseCase
import com.example.quizzard.domain.use_case.local.InsertUserUseCase
import com.example.quizzard.domain.use_case.local.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideUserDao(userDataBase: UserDatabase): UserDao {
        return userDataBase.userDao()
    }

    @Provides
    fun provideLocalQuizRepository(userDao: UserDao): LocalQuizRepository {
        return LocalQuizRepositoryImpl(userDao)
    }

    @Provides
    fun provideUserUseCases(localQuizRepository: LocalQuizRepository): UserUseCases {
        return UserUseCases(
            getUserUseCase = GetUserUseCase(localQuizRepository),
            insertUserUseCase = InsertUserUseCase(localQuizRepository)
        )
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): RoomDatabase {
        return Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user.db"
        )
            .addTypeConverter(Converters())
            .fallbackToDestructiveMigration()
            .build()
    }
}