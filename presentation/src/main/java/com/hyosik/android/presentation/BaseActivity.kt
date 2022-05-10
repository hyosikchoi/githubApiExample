package com.hyosik.android.presentation

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutResId : Int) : AppCompatActivity(layoutResId) {

    protected lateinit var binding : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , layoutResId)
        performDataBinding()
    }

    private fun performDataBinding() = with(binding) {
        lifecycleOwner = this@BaseActivity
        executePendingBindings()
    }

}