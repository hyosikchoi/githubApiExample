package com.hyosik.android.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.hyosik.android.presentation.adapter.GithubRepositoryAdapter
import com.hyosik.android.presentation.adapter.ReposLoadStateAdapter
import com.hyosik.android.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
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
        lifecycleScope.launch {
            viewModel.repos.collect {
                githubRepositoryAdapter.submitData(it)
            }
        }
    }

}