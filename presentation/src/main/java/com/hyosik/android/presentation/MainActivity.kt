package com.hyosik.android.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.hyosik.android.presentation.adapter.GithubRepositoryAdapter
import com.hyosik.android.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.githubRecyclerView.adapter = GithubRepositoryAdapter()
        init()
    }

    private fun init() {
        viewModel.getRepo("Android" , 1 , 20)
    }

}