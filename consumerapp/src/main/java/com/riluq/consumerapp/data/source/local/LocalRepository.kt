package com.riluq.consumerapp.data.source.local

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.riluq.consumerapp.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LocalRepository @Inject constructor(
    context: Context
) {

    private var contentResolver: ContentResolver? = null

    init {
        contentResolver = context.contentResolver
    }

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        withContext(Dispatchers.IO) {
            contentResolver?.insert(
                DbContract.CONTENT_URI,
                favorite(
                    null,
                    favoriteEntity.username,
                    favoriteEntity.photo,
                    favoriteEntity.htmlUrl
                )
            )
        }
    }

    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        withContext(Dispatchers.IO) {
            val deleteUri: Uri =
                ContentUris.withAppendedId(DbContract.CONTENT_URI, favoriteEntity.id!!)
            contentResolver?.delete(deleteUri, null, null)
        }
    }

    suspend fun getAllFavorite(): Cursor? {
        return withContext(Dispatchers.IO) {
            contentResolver?.query(
                DbContract.CONTENT_URI, null, null, null, null
            )
        }
    }

    suspend fun getFavoriteByUsername(username: String): Cursor? {
        return withContext(Dispatchers.IO) {
            contentResolver?.query(
                DbContract.CONTENT_URI, arrayOf(username), null, null, null
            )
        }
    }

    fun favorite(id: Long?, username: String?, photo: String?, htmlUrl: String?): ContentValues {
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("username", username)
        contentValues.put("photo", photo)
        contentValues.put("html_url", htmlUrl)
        return contentValues
    }
}