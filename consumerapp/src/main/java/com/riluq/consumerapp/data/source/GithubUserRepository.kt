package com.riluq.consumerapp.data.source

import android.database.Cursor
import com.riluq.consumerapp.data.source.local.LocalRepository
import com.riluq.consumerapp.data.source.local.entity.FavoriteEntity
import javax.inject.Inject

class GithubUserRepository @Inject constructor(
    private val localRepository: LocalRepository
) : GithubUserDataSource {

    override suspend fun getAllFavorite(): Cursor? =
        localRepository.getAllFavorite()

    override suspend fun getFavorite(username: String): Cursor? =
        localRepository.getFavoriteByUsername(username)

    override suspend fun addFavorite(favoriteEntity: FavoriteEntity) =
        localRepository.insertFavorite(favoriteEntity)

    override suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) =
        localRepository.deleteFavorite(favoriteEntity)

}