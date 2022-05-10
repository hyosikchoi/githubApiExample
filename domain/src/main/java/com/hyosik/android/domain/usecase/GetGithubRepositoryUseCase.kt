package com.hyosik.android.domain.usecase

import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.domain.repository.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GetGithubRepositoryUseCase(private val githubRepository: GithubRepository) {

    suspend operator fun invoke(
        query: String,
        page: Int,
        perPage: Int,
    ) : Flow<List<GithubRepo>> {
        return githubRepository.getRepository(query = query, page = page, perPage = perPage)
    }
}

