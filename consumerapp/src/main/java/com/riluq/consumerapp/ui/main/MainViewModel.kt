package com.riluq.consumerapp.ui.main

import android.database.Cursor
import androidx.lifecycle.viewModelScope
import com.riluq.consumerapp.data.source.GithubUserRepository
import com.riluq.consumerapp.data.source.local.entity.FavoriteEntity
import com.riluq.consumerapp.ui.base.BaseViewModel
import com.riluq.consumerapp.utils.MappingHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

    fun getFavorites(): Cursor? = runBlocking {
        repository.getAllFavorite()
    }

    fun deleteFavorite(favoriteEntity: FavoriteEntity, adapter: FavoriteAdapter) {
        viewModelScope.launch {
            repository.deleteFavorite(favoriteEntity)
            adapter.submitList(MappingHelper.mapCursorToArrayList(getFavorites()))
            adapter.notifyDataSetChanged()
        }
    }

}