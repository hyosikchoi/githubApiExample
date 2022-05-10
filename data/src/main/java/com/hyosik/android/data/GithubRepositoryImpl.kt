package com.hyosik.android.data

import com.hyosik.android.data.model.Repo
import com.hyosik.android.data.source.GithubRemoteSource
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubRemoteSource : GithubRemoteSource
) : GithubRepository {

    override suspend fun getRepository(
        query: String,
        page: Int,
        perPage: Int
    ): Flow<List<GithubRepo>> {
        return githubRemoteSource.getRepo(
            query = query,
            page = page,
            perPage = perPage
        )
    }
}