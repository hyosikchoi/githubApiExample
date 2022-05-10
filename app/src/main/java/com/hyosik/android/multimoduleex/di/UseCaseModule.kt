package com.hyosik.android.multimoduleex.di

import com.hyosik.android.domain.repository.GithubRepository
import com.hyosik.android.domain.usecase.GetGithubRepositoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun providesGetGithubRepositoryUseCase(repository: GithubRepository) : GetGithubRepositoryUseCase {
        return GetGithubRepositoryUseCase(githubRepository = repository)
    }

}