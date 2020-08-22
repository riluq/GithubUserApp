package com.riluq.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.riluq.githubuserapp.data.source.GithubUserRepository
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity
import com.riluq.githubuserapp.ui.base.BaseViewModel
import com.riluq.githubuserapp.utils.LayoutState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(repository: GithubUserRepository) : BaseViewModel(repository) {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String>
        get() = _photo

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _company = MutableLiveData<String>()
    val company: LiveData<String>
        get() = _company

    private val _location = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _location

    private val _layoutState = MutableLiveData<LayoutState>()
    val layoutState: LiveData<LayoutState>
        get() = _layoutState

    private val _hasFavorite = MutableLiveData<Boolean>()
    val hasFavorite: LiveData<Boolean>
        get() = _hasFavorite

    fun detail() = liveData(Dispatchers.IO) {
        emitSource(repository.onGetDetail(username.value))
    }

    fun followers(username: String) = liveData(Dispatchers.IO) {
        emitSource(repository.onGetFollowers(username))
    }

    fun following(username: String) = liveData(Dispatchers.IO) {
        emitSource(repository.onGetFollowing(username))
    }

    fun getFavoriteByName(username: String): LiveData<FavoriteEntity> =
        repository.getFavorite(username)

    fun addProduct(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repository.addFavorite(favoriteEntity)
        }
    }

    fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repository.deleteFavorite(favoriteEntity)
        }
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setPhoto(photo: String?) {
        _photo.value = photo ?: "-"
    }

    fun setName(name: String?) {
        _name.value = name ?: "-"
    }

    fun setEmail(email: String?) {
        _email.value = email ?: "-"
    }

    fun setCompany(company: String?) {
        _company.value = company ?: "-"
    }

    fun setLocation(location: String?) {
        _location.value = location ?: "-"
    }

    fun setLayoutState(layoutState: LayoutState) {
        _layoutState.value = layoutState
    }

    fun setFavoriteState(favoriteState: Boolean) {
        _hasFavorite.value = favoriteState
    }

}