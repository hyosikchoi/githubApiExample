package com.hyosik.android.presentation

import com.hyosik.android.domain.model.GithubRepo

sealed class State {

    open val repoList : List<GithubRepo> = emptyList()

    object UnInitialized : State()

    object Loading : State()

    data class Success(
       override val repoList : List<GithubRepo>
    ) : State()

    object Error : State()

}
