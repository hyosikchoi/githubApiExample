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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGithubRepositoryUseCase: GetGithubRepositoryUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.UnInitialized)
    val state: StateFlow<State> = _state.asStateFlow()
    val scope: CoroutineScope get() = viewModelScope

    override fun fetchData(): Job = viewModelScope.launch(Dispatchers.IO) {
        if(_state.value !is State.Success) {
            _state.value = State.Loading
            val repo = getGithubRepositoryUseCase(query = "Android", page = 1, perPage = 30).cachedIn(
                viewModelScope
            )
            repo
                .catch {
                    _state.value = State.Error
                }.collect {
                    _state.value = State.Success(it)
                }
        }
    }
}