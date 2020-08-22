package com.riluq.githubuserapp.data.source.local.entity

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riluq.githubuserapp.data.source.local.room.GithubUserDatabase
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = GithubUserDatabase.FAVORITE_TABLE_NAME)
data class FavoriteEntity(
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Long?,

    @field:ColumnInfo(name = "username")
    val username: String?,

    @field:ColumnInfo(name = "photo")
    val photo: String?,

    @field:ColumnInfo(name = "html_url")
    val htmlUrl: String?
) : Parcelable {
    companion object {
        fun fromContentValues(values: ContentValues): FavoriteEntity {
            var id: Long? = null
            var username: String? = null
            var photo: String? = null
            var htmlUrl: String? = null

            if (values.containsKey("id")) {
                id = values.getAsLong("id")
            }
            if (values.containsKey("username")) {
                username = values.getAsString("username")
            }
            if (values.containsKey("photo")) {
                photo = values.getAsString("photo")
            }
            if (values.containsKey("html_url")) {
                htmlUrl = values.getAsString("html_url")
            }

            return FavoriteEntity(id, username, photo, htmlUrl)
        }
    }
}