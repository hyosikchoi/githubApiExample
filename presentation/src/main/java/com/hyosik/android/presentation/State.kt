package com.hyosik.android.presentation

import com.hyosik.android.domain.model.GithubRepo

sealed class State {
    object UnInitialized : State()

    object Loading : State()

    data class Success(
        val repoList : List<GithubRepo>
    ) : State()

    object Error : State()

}
