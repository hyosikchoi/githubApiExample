package com.hyosik.android.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.hyosik.android.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        observeData()
    }

    private fun init() {
        viewModel.getRepo("Android" , 1 , 20)
    }

    private fun observeData()  {
        lifecycleScope.launch {
            viewModel.repoListStateFlow.collect { state ->
                when(state) {
                    is State.UnInitialized -> {}
                    is State.Loading -> {}
                    is State.Success -> { Log.d("repo" , state.repoList.toString())}
                    is State.Error -> {}
                }
            }
        }
    }

}