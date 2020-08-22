package com.riluq.githubuserapp.vo

import com.riluq.githubuserapp.data.source.remote.response.ErrorResponse

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val errorResponse: ErrorResponse?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.DONE, data, null, null)
        }

        fun <T> errorResponse(errorResponse: ErrorResponse?): Resource<T> {
            return Resource(Status.ERROR, null, null, errorResponse)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg, null)
        }

    }
}