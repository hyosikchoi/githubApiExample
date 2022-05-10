package com.hyosik.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyosik.android.domain.model.GithubRepo
import com.hyosik.android.domain.usecase.GetGithubRepositoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGithubRepositoryUseCase : GetGithubRepositoryUseCase
) : ViewModel() {

    private var _repoListStateFlow = MutableStateFlow<State>(State.UnInitialized)

    val repoListStateFlow : StateFlow<State> = _repoListStateFlow


    fun getRepo(query : String , page : Int , perPage : Int) = viewModelScope.launch {
        getGithubRepositoryUseCase(query = query , page = page , perPage = perPage)
            .onStart {
                _repoListStateFlow.value = State.Loading
            }
            .catch { exception ->
                _repoListStateFlow.value = State.Error
            }
            .collect { value: List<GithubRepo> ->
                _repoListStateFlow.value = State.Success(value)
            }
    }


}