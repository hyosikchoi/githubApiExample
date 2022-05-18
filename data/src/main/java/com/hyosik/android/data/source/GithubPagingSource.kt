package com.hyosik.android.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyosik.android.data.BuildConfig
import com.hyosik.android.data.model.Repo
import com.hyosik.android.data.remote.GithubApiService
import com.hyosik.android.domain.model.GithubRepo
import retrofit2.HttpException
import java.io.IOException
import java.lang.NullPointerException

class GithubPagingSource(
 private val apiService: GithubApiService,
 private val page : Int,
 private val perPage: Int,
 private val query: String
) : PagingSource<Int,GithubRepo>() {

    override fun getRefreshKey(state: PagingState<Int, GithubRepo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepo> {
        val position = params.key ?: page
        val apiQuery = query
        return try {
            val response = apiService.getRepository(BuildConfig.TOKEN,apiQuery,position,params.loadSize)
            val repos = response.body()!!.items
            val nextKey = if(repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / perPage)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if(position == page) null else position -1,
                nextKey = nextKey
            )
        } catch (exception : IOException) {
            LoadResult.Error(exception)
        } catch (exception : HttpException) {
            LoadResult.Error(exception)
        } catch (exception : NullPointerException) {
            LoadResult.Error(exception)
        }
    }

}