package com.riluq.githubuserapp.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.riluq.githubuserapp.data.source.GithubUserRepository
import com.riluq.githubuserapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers

class HomeViewModel(repository: GithubUserRepository) : BaseViewModel(repository) {

    private val _searchQuery = MutableLiveData<String?>()
    val searchQuery: LiveData<String?>
        get() = _searchQuery

    fun searchUsername() = liveData(Dispatchers.IO) {
        emitSource(repository.onSearch(searchQuery.value))
    }

    fun setSearhQuery(username: String?) {
        _searchQuery.value = username
    }
}