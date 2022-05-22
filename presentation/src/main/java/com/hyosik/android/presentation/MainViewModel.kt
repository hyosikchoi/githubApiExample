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
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.domain.usecase.GetGithubRepositoryUseCase
import com.hyosik.android.presentation.adapter.GithubRepositoryAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGithubRepositoryUseCase: GetGithubRepositoryUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.UnInitialized)
    val state: StateFlow<State> = _state.asStateFlow()
    val scope: CoroutineScope get() = viewModelScope

    fun fetchGithubRepositories(query: String, page: Int, perPage: Int) {
          viewModelScope.launch(Dispatchers.IO) {
            _state.value = State.Loading
            val repo = getGithubRepositoryUseCase(query = query, page = page, perPage = perPage).cachedIn(viewModelScope)
            repo
                .catch {
                    _state.value = State.Error
                }.collect {
                    _state.value = State.Success(it)
                }
        }
    }
}