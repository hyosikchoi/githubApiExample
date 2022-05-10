package com.hyosik.android.multimoduleex.di

import com.hyosik.android.data.remote.GithubApiService
import com.hyosik.android.data.source.GithubRemoteSource
import com.hyosik.android.data.source.GithubRemoteSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun providesGithubRemoteSource(githubApiService : GithubApiService) : GithubRemoteSource {
        return GithubRemoteSourceImpl(githubApiService = githubApiService)
    }

}