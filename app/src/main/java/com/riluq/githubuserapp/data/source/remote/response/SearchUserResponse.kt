package com.riluq.githubuserapp.data.source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchUserResponse(
    @Json(name = "items")
    val data: List<User>
) : Parcelable