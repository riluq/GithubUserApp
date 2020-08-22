package com.riluq.githubuserapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity
import com.riluq.githubuserapp.data.source.remote.response.DetailUserResponse
import com.riluq.githubuserapp.data.source.remote.response.SearchUserResponse
import com.riluq.githubuserapp.data.source.remote.response.User
import com.riluq.githubuserapp.vo.Resource

interface GithubUserDataSource {

    suspend fun onSearch(username: String?): LiveData<Resource<SearchUserResponse>>
    suspend fun onGetDetail(username: String?): LiveData<Resource<DetailUserResponse>>
    suspend fun onGetFollowers(username: String?): LiveData<Resource<List<User>>>
    suspend fun onGetFollowing(username: String?): LiveData<Resource<List<User>>>

    // Database
    // Favorite
    fun getAllFavorite(): LiveData<PagedList<FavoriteEntity>>
    fun getFavorite(username: String): LiveData<FavoriteEntity>
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

    // Shared Preferences
    fun getReminder(): Boolean
    fun setReminder(reminderState: Boolean)
}