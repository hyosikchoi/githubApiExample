package com.hyosik.android.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hyosik.android.data.model.Repo
import com.hyosik.android.data.remote.GithubApiService
import com.hyosik.android.data.source.GithubPagingSource
import com.hyosik.android.data.source.GithubRemoteSource
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : GithubRepository {

    override fun getRepository(
        query: String,
        page: Int,
        perPage: Int
    ): Flow<PagingData<GithubRepo>> {
        return Pager(
            config = PagingConfig(pageSize = perPage),
            pagingSourceFactory = { GithubPagingSource(apiService,page,perPage,query)}
        ).flow
    }
}