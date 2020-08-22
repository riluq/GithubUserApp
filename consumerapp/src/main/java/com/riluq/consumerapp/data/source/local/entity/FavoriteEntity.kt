package com.riluq.consumerapp.data.source.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteEntity(
    val id: Long?,

    val username: String?,

    val photo: String?,

    val htmlUrl: String?
) : Parcelable