package com.riluq.consumerapp.data.source.local

import android.database.Cursor
import android.net.Uri

class DbContract {

    companion object {
        val AUTHORITY = "com.riluq.githubuserapp"

        const val FAVORITE_TABLE_NAME = "favorite"
        const val SCHEME = "content"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(FAVORITE_TABLE_NAME)
            .build()

        fun getFavorite(cursor: Cursor?, column: String?): String? {
            return cursor?.getString(cursor.getColumnIndex(column))
        }
    }

}