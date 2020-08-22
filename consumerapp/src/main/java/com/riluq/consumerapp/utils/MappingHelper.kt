package com.riluq.consumerapp.utils

import android.database.Cursor
import com.riluq.consumerapp.data.source.local.entity.FavoriteEntity

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FavoriteEntity> {
        val notesList = ArrayList<FavoriteEntity>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val username = getString(getColumnIndexOrThrow("username"))
                val photo = getString(getColumnIndexOrThrow("photo"))
                val htmlUrl = getString(getColumnIndexOrThrow("html_url"))
                notesList.add(FavoriteEntity(id, username, photo, htmlUrl))
            }
        }
        return notesList
    }

    fun mapCursorToObject(notesCursor: Cursor?): FavoriteEntity {
        var note = FavoriteEntity(null, null, null, null)
        notesCursor?.apply {
            moveToFirst()
            val id = getLong(getColumnIndexOrThrow("id"))
            val username = getString(getColumnIndexOrThrow("username"))
            val photo = getString(getColumnIndexOrThrow("photo"))
            val htmlUrl = getString(getColumnIndexOrThrow("html_url"))
            note = FavoriteEntity(id, username, photo, htmlUrl)
        }
        return note
    }
}