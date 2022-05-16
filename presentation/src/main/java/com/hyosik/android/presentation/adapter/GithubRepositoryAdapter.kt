package com.hyosik.android.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.presentation.databinding.ItemGithubViewHolderBinding

class GithubRepositoryAdapter : ListAdapter<GithubRepo, GithubRepositoryAdapter.GitHubViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubViewHolder {
        return GitHubViewHolder(ItemGithubViewHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: GitHubViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class GitHubViewHolder(private val binding : ItemGithubViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: GithubRepo) = with(binding) {
            githubRepo = repo
            executePendingBindings()
        }

    }

    companion object  {
        val diffUtil = object : DiffUtil.ItemCallback<GithubRepo>() {

            override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
                return oldItem == newItem
            }
        }
    }

}