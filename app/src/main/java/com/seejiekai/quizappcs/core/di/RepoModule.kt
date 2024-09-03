package com.seejiekai.quizappcs.core.di


import com.seejiekai.quizappcs.core.service.AuthService
import com.seejiekai.quizappcs.data.repo.QuizRepo
import com.seejiekai.quizappcs.data.repo.QuizRepoFirebase
import com.seejiekai.quizappcs.data.repo.ResultRepo
import com.seejiekai.quizappcs.data.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Provides
    @Singleton
    fun provideTodoRepo(authService: AuthService): QuizRepo {
        return QuizRepoFirebase(authService)

    }@Provides
    @Singleton
    fun provideUserRepo(): UserRepo {
        return UserRepo()
    }

    @Provides
    @Singleton
    fun provideResultRepo(): ResultRepo {
        return ResultRepo()
    }
}