package com.hyosik.android.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.hyosik.android.presentation.adapter.GithubRepositoryAdapter
import com.hyosik.android.presentation.adapter.ReposLoadStateAdapter
import com.hyosik.android.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    private val githubRepositoryAdapter = GithubRepositoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.githubRecyclerView.setHasFixedSize(false)
        binding.adapter = githubRepositoryAdapter
        init()
    }

    private fun init() {
        viewModel.fetchGithubRepositories("Android" , 1 , 30)
    }

    fun clickOfRetry() {
        githubRepositoryAdapter.retry()
    }

}