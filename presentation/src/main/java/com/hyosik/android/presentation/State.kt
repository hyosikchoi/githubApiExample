package com.hyosik.android.presentation

import androidx.paging.PagingData
import com.hyosik.android.domain.model.GithubRepo

sealed class State {

    open val repoList: PagingData<GithubRepo> = PagingData.empty()

    val isLoading: Boolean get() = this is Loading
    val isEmpty: Boolean get() = this is Empty
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    object UnInitialized : State()

    object Loading : State()

    object Empty : State()

    data class Success(
        override val repoList: PagingData<GithubRepo>
    ) : State()

    object Error : State()

}
