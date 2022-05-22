package com.hyosik.android.presentation.adapter

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.presentation.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items", "scope", "adapter")
fun RecyclerView.setGithubRepo(
    state: State?,
    scope: CoroutineScope?,
    adapter: GithubRepositoryAdapter?
) {
//    val adapter = adapter as? GithubRepositoryAdapter
    adapter?.let { githubRepoAdapter ->
        this.adapter = githubRepoAdapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { githubRepoAdapter.retry() },
            footer = ReposLoadStateAdapter { githubRepoAdapter.retry() }
        )
        if(state is State.Success) {
            scope?.launch {
               githubRepoAdapter.submitData(state.repoList)
            }
        }

    }

}

@BindingAdapter("android:text")
fun TextView.setText(loadState: LoadState?) {
    if (loadState is LoadState.Error) {
        this.text = loadState.error.localizedMessage
    } else {
        this.text = ""
    }
}

@BindingAdapter("android:visibility")
fun View.setVisible(isVisible : Boolean?) {
    isVisible?.let {
        this.isVisible = it
    }
}