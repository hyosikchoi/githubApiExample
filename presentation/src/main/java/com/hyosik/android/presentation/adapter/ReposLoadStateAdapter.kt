/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyosik.android.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosik.android.presentation.R
import com.hyosik.android.presentation.databinding.ReposLoadStateFooterViewItemBinding

class ReposLoadStateAdapter(
        private val retry: () -> Unit
) : LoadStateAdapter<ReposLoadStateAdapter.ReposLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ReposLoadStateViewHolder {
        return ReposLoadStateViewHolder(ReposLoadStateFooterViewItemBinding.bind(LayoutInflater.from(parent.context).inflate(
            R.layout.repos_load_state_footer_view_item,parent,false)) , retry = retry)
    }

   inner class ReposLoadStateViewHolder(
       private val binding: ReposLoadStateFooterViewItemBinding,
       retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.also {
                it.setOnClickListener { retry.invoke() }
            }
        }

        fun bind(loadState: LoadState) {
            binding.loadState = loadState
        }

    }

}
