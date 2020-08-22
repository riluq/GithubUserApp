package com.riluq.githubuserapp.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "message")
    val message: String?,
    @Json(name = "documentation_url")
    val documentationUrl: String?
)