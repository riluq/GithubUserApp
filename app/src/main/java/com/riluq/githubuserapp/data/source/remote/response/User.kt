package com.riluq.githubuserapp.data.source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    val id: String?,

    @Json(name = "login")
    val username: String?,

    @Json(name = "avatar_url")
    val photo: String?,

    @Json(name = "html_url")
    val htmlUrl: String?
) : Parcelable
