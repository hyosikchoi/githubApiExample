package com.hyosik.android.presentation.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.presentation.State
import kotlinx.coroutines.flow.StateFlow


@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun RecyclerView.setGithubRepo(repoList : List<GithubRepo>?) {
    val adapter = adapter as? ListAdapter<GithubRepo,*>
    adapter?.submitList(repoList)
}