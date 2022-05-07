package com.hyosik.android.data.remote

import com.hyosik.android.data.model.GithubRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    suspend fun getRepository(
        @Header("Authorization") token : String,
        @Query("q") query : String,
        @Query("page") page : Int,
        @Query("per_page") perPage : Int
    ) : Response<GithubRes>

}