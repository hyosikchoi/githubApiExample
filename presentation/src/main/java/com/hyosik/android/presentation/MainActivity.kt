package com.hyosik.android.presentation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    private val githubRepositoryAdapter = GithubRepositoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.githubRecyclerView.setHasFixedSize(false)
        binding.adapter = githubRepositoryAdapter
        init()
    }

    private fun init() {
        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideSoftInput(v)
                viewModel.searchRepository(query = v.text.toString())
            }
            true
        }

        binding.searchImageButton.setOnClickListener {
            hideSoftInput(binding.searchEditText)
            val query = binding.searchEditText.text.toString()
            viewModel.searchRepository(query = query)

        }
        binding.retryButton.setOnClickListener {
            githubRepositoryAdapter.retry()
        }

        lifecycleScope.launch {
            githubRepositoryAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && githubRepositoryAdapter.itemCount == 0
                // show empty list
                binding.noResultTextView.isVisible = isListEmpty
                // Only show the list if refresh succeeds.
                binding.githubRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            }

        }

    }

    private fun hideSoftInput(v : View) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken,0)
    }

}