package com.hyosik.android.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        binding.githubRecyclerView.setHasFixedSize(true)
        init()
        binding.githubRecyclerView.adapter = githubRepositoryAdapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { githubRepositoryAdapter.retry() },
                footer = ReposLoadStateAdapter { githubRepositoryAdapter.retry() }
            )

    }

    private fun init() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.repos.collect {
                githubRepositoryAdapter.submitData(it)
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            githubRepositoryAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty : Boolean = loadState.refresh is LoadState.NotLoading && githubRepositoryAdapter.itemCount == 0

                binding.noResultTextView.isVisible = isListEmpty
                binding.githubRecyclerView.isVisible = !isListEmpty
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            }
        }
    }

    fun clickOfRetry() {
        githubRepositoryAdapter.retry()
    }

}