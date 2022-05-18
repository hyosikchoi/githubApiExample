package com.hyosik.android.presentation

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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

    private val _repoListStateFlow = MutableStateFlow<State>(State.UnInitialized)
    private val _progressView = MutableStateFlow<Int>(View.GONE)

    val repoListStateFlow : StateFlow<State> get() =  _repoListStateFlow.asStateFlow()
    val progressView : StateFlow<Int> get() =  _progressView.asStateFlow()

    val repos = getGithubRepositoryUseCase(query = "Android" , page = 1 , perPage = 20)

//    fun getRepo(query : String , page : Int , perPage : Int) =

    fun showProgress() {
        _progressView.value = View.VISIBLE
    }

    fun hideProgress() {
        _progressView.value = View.GONE
    }

//    viewModelScope.launch {
//        getGithubRepositoryUseCase(query = query , page = page , perPage = perPage)
//            .onStart {
//                showProgress()
//            }
//            .onCompletion {
//                hideProgress()
//            }
//            .catch { exception ->
//                _repoListStateFlow.value = State.Error
//            }
//            .collect { value: List<GithubRepo> ->
//                _repoListStateFlow.value = State.Success(value)
//            }
//    }

}