package com.hyosik.android.domain.repository

import androidx.paging.PagingData
import com.hyosik.android.domain.model.GithubRepo
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

     fun getRepository(query : String , page : Int , perPage : Int) : Flow<PagingData<GithubRepo>>

}