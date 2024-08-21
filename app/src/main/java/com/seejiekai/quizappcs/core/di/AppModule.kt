package com.seejiekai.quizappcs.core.di

import android.content.Context
import com.seejiekai.quizappcs.core.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuthService(@ApplicationContext context: Context): AuthService {
        return AuthService(context = context)
    }
}