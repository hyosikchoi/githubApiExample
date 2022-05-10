package com.hyosik.android.data.model

data class GithubRes(
    val incomplete_results: Boolean,
    val items: List<Repo>,
    val total_count: Int
)