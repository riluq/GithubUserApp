package com.riluq.consumerapp.data.source

import android.database.Cursor
import com.riluq.consumerapp.data.source.local.entity.FavoriteEntity

interface GithubUserDataSource {
    // Database
    // Favorite
    suspend fun getAllFavorite(): Cursor?
    suspend fun getFavorite(username: String): Cursor?
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)
}