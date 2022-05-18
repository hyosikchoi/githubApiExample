package com.hyosik.android.multimoduleex.di

import com.hyosik.android.data.GithubRepositoryImpl
import com.hyosik.android.data.remote.GithubApiService
import com.hyosik.android.data.source.GithubRemoteSource
import com.hyosik.android.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesGithubRepository(apiService: GithubApiService) : GithubRepository {
        return GithubRepositoryImpl(apiService = apiService)
    }

}