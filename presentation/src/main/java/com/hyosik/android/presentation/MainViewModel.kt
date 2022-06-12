package com.hyosik.android.presentation

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.collect
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.domain.usecase.GetGithubRepositoryUseCase
import com.hyosik.android.presentation.adapter.GithubRepositoryAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGithubRepositoryUseCase: GetGithubRepositoryUseCase
) : BaseViewModel() {
    private val _repo : MutableStateFlow<PagingData<GithubRepo>> = MutableStateFlow(PagingData.empty())
    val repo = _repo.asStateFlow()
    val scope: CoroutineScope get() = viewModelScope

    private var query = "Android"
    private var searchMode : SearchMode = SearchMode.SEARCH

    override fun fetchData(): Job = viewModelScope.launch(Dispatchers.IO) {
        if(searchMode == SearchMode.SEARCH) {
            getGithubRepositoryUseCase(query = query, page = 1, perPage = 30).cachedIn(
                viewModelScope
            ).catch { error ->
                 LoadState.Error(error)
                 searchMode = SearchMode.READ
            }.collect {
                _repo.value = it
                searchMode = SearchMode.READ
            }

        }
    }

    fun searchRepository(query : String) {
        this.query = query
        searchMode = SearchMode.SEARCH
        fetchData()
    }
}