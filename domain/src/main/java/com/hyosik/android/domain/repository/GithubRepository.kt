package com.hyosik.android.domain.repository

import com.hyosik.android.domain.model.GithubRepo
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    suspend fun getRepository(query : String , page : Int , perPage : Int) : Flow<List<GithubRepo>>

}