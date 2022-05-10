package com.hyosik.android.data.source

import com.hyosik.android.data.BuildConfig
import com.hyosik.android.data.model.Repo
import com.hyosik.android.data.remote.GithubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GithubRemoteSource {

    suspend fun getRepo(
        query: String,
        page: Int,
        perPage: Int
    ) : Flow<List<Repo>>

}

class GithubRemoteSourceImpl @Inject constructor(
    private val githubApiService: GithubApiService
) : GithubRemoteSource {

    override suspend fun getRepo(query: String, page: Int, perPage: Int): Flow<List<Repo>> {
        return flow<List<Repo>> {
            val response = githubApiService.getRepository(
                token = BuildConfig.TOKEN,
                query = query,
                page = page,
                perPage = perPage
            )

            if(response.isSuccessful) {
                response.body()?.let {
                    emit(it.items)
                }
            } else {
                emit(emptyList())
            }
        }.flowOn(Dispatchers.IO)
    }
}