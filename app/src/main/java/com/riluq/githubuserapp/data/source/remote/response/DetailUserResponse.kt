package com.riluq.githubuserapp.data.source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DetailUserResponse(
    @Json(name = "login")
    val username: String?,
    @Json(name = "avatar_url")
    val photo: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "company")
    val company: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "followers")
    val followers: String?,
    @Json(name = "following")
    val following: String?,
    @Json(name = "public_repos")
    val public_repos: String?
) : Parcelable