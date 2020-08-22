package com.riluq.githubuserapp.ui.main

import com.riluq.githubuserapp.data.source.GithubUserRepository
import com.riluq.githubuserapp.ui.base.BaseViewModel

class MainViewModel(repository: GithubUserRepository) : BaseViewModel(repository) {

//    private val _searchQuery = MutableLiveData<String?>()
//    val searchQuery: LiveData<String?>
//        get() = _searchQuery
//
//    fun searchUsername() = liveData(Dispatchers.IO) {
//        emitSource(repository.onSearch(searchQuery.value))
//    }
//
//    fun setSearhQuery(username: String?) {
//        _searchQuery.value = username
//    }

}