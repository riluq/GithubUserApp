package com.riluq.githubuserapp.data.source.local

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity
import com.riluq.githubuserapp.data.source.local.prefs.AppPreferences
import com.riluq.githubuserapp.data.source.local.room.GithubUserDatabase
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val githubUserDatabase: GithubUserDatabase,
    private val appPreferences: AppPreferences
) {

    @WorkerThread
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        githubUserDatabase.githubUserDao().insert(favoriteEntity)
    }

    @WorkerThread
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        githubUserDatabase.githubUserDao().delete(favoriteEntity)
    }

    fun getAllFavorite(): DataSource.Factory<Int, FavoriteEntity> {
        return githubUserDatabase.githubUserDao().getAllFavorite()
    }

    fun getFavoriteByUsername(username: String): LiveData<FavoriteEntity> {
        return githubUserDatabase.githubUserDao().getFavoriteByUsername(username)
    }

    fun preferences() = appPreferences
}