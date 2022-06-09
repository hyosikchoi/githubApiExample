package com.hyosik.android.presentation

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.Job

abstract class BaseActivity<DB : ViewDataBinding , VM : BaseViewModel>(@LayoutRes private val layoutResId : Int) : AppCompatActivity(layoutResId) {

    abstract val viewModel : VM

    protected lateinit var binding : DB

    private lateinit var fetchJob : Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , layoutResId)
        performDataBinding()
        fetchJob = viewModel.fetchData()
    }

    private fun performDataBinding() = with(binding) {
        lifecycleOwner = this@BaseActivity
        executePendingBindings()
    }

    override fun onDestroy() {
        if(fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()

    }

}