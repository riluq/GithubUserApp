package com.riluq.githubuserapp.data.source.local.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity
import com.riluq.githubuserapp.data.source.local.room.GithubUserDao
import com.riluq.githubuserapp.data.source.local.room.GithubUserDatabase
import kotlinx.coroutines.runBlocking


class GithubUserProvider : ContentProvider() {

    private lateinit var database: GithubUserDatabase

    companion object {
        const val AUTHORITY = "com.riluq.githubuserapp"

        private const val CODE_FAVORITE_DIR = 1

        private const val CODE_FAVORITE_ITEM = 2

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, GithubUserDatabase.FAVORITE_TABLE_NAME, CODE_FAVORITE_DIR);
            MATCHER.addURI(
                AUTHORITY,
                GithubUserDatabase.FAVORITE_TABLE_NAME + "/*",
                CODE_FAVORITE_ITEM
            );
        }
    }


    override fun onCreate(): Boolean {
        if (context != null) {
            database = Room.databaseBuilder(
                context!!,
                GithubUserDatabase::class.java, GithubUserDatabase.DATABASE_NAME
            ).build()
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val code = MATCHER.match(uri)
        return if (code == CODE_FAVORITE_DIR || code == CODE_FAVORITE_ITEM) {
            val context = context ?: return null
            val githubUserDao: GithubUserDao = database.githubUserDao()
            val cursor: Cursor
            cursor = if (code == CODE_FAVORITE_DIR) {
                githubUserDao.getAllFavoriteProvider()
            } else {
                githubUserDao.getFavoriteByUsernameProvider(ContentValues().getAsString("username"))
            }
            cursor.setNotificationUri(context.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (MATCHER.match(uri)) {
            CODE_FAVORITE_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_FAVORITE_ITEM -> {
                val context = context ?: return 0
                val count: Int = runBlocking {
                    database.githubUserDao()
                        .delete(ContentUris.parseId(uri))
                }
                context.contentResolver.notifyChange(uri, null)
                count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            CODE_FAVORITE_DIR -> "vnd.android.cursor.dir/" + AUTHORITY + "." + GithubUserDatabase.FAVORITE_TABLE_NAME
            CODE_FAVORITE_ITEM -> "vnd.android.cursor.item/" + AUTHORITY + "." + GithubUserDatabase.FAVORITE_TABLE_NAME
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (MATCHER.match(uri)) {
            CODE_FAVORITE_DIR -> {
                val context = context ?: return null
                val id: Long = runBlocking {
                    database.githubUserDao()
                        .insert(FavoriteEntity.fromContentValues(values!!))
                }
                context.contentResolver.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, id)
            }
            CODE_FAVORITE_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return when (MATCHER.match(uri)) {
            CODE_FAVORITE_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_FAVORITE_ITEM -> {
                val context = context ?: return 0
                val cheese: FavoriteEntity = FavoriteEntity.fromContentValues(values!!)
                cheese.id = ContentUris.parseId(uri)
                val count: Int = runBlocking {
                    database.githubUserDao().update(cheese)
                }
                context.contentResolver.notifyChange(uri, null)
                count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}
