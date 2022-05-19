package com.hyosik.android.presentation.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.presentation.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// 현재 사용 안함.
@Suppress("UNCHECKED_CAST")
@BindingAdapter("items", "scope")
fun RecyclerView.setGithubRepo(repoList : Flow<PagingData<GithubRepo>>?, scope: CoroutineScope?) {
    val adapter = adapter as? PagingDataAdapter<GithubRepo,*>
    scope?.launch {
        repoList?.collect {
            adapter?.submitData(it)
        }
    }
}

@BindingAdapter("android:text")
fun TextView.setText(loadState : LoadState?) {
    if(loadState is LoadState.Error) {
        this.text = loadState.error.localizedMessage
    } else {
        this.text = ""
    }
}