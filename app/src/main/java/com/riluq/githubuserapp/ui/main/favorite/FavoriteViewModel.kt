package com.riluq.githubuserapp.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.riluq.githubuserapp.data.source.GithubUserRepository
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity
import com.riluq.githubuserapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoriteViewModel(repository: GithubUserRepository) : BaseViewModel(repository) {
    fun getFavorites(): LiveData<PagedList<FavoriteEntity>> {
        return repository.getAllFavorite()
    }

    fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repository.deleteFavorite(favoriteEntity)
        }
    }
}