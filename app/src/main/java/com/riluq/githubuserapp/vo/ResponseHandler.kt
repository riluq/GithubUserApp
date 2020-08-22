package com.riluq.githubuserapp.vo

import com.riluq.githubuserapp.data.source.remote.response.ErrorResponseJsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject


enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

open class ResponseHandler @Inject constructor(private val moshi: Moshi) {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleError(data: ResponseBody): Resource<T> {
        val s = ErrorResponseJsonAdapter(moshi = moshi)
        val dataJson = s.fromJson(data.string())
        return Resource.errorResponse(dataJson)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}