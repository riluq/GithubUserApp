package com.riluq.githubuserapp.data.source.remote

import com.riluq.githubuserapp.data.source.remote.response.DetailUserResponse
import com.riluq.githubuserapp.data.source.remote.response.SearchUserResponse
import com.riluq.githubuserapp.data.source.remote.response.User
import com.riluq.githubuserapp.vo.Resource
import com.riluq.githubuserapp.vo.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val api: ApiService,
    private val responseHandler: ResponseHandler
) {

    suspend fun searchUser(
        username: String?,
        callback: ResponseCallback<SearchUserResponse>
    ) {
        withContext(Dispatchers.IO) {
            val search = api.search(username)
            try {
                if (search.isSuccessful) {
                    callback.onComplete(responseHandler.handleSuccess(search.body()!!))
                } else {
                    callback.onErrorResponse(responseHandler.handleError(search.errorBody()!!))
                }
            } catch (exception: Exception) {
                callback.onFailed(responseHandler.handleException(exception), exception)
            }
        }
    }

    suspend fun detailUser(
        username: String?,
        callback: ResponseCallback<DetailUserResponse>
    ) {
        withContext(Dispatchers.IO) {
            val detail = api.detailUser(username)
            try {
                if (detail.isSuccessful) {
                    callback.onComplete(responseHandler.handleSuccess(detail.body()!!))
                } else {
                    callback.onErrorResponse(responseHandler.handleError(detail.errorBody()!!))
                }
            } catch (exception: Exception) {
                callback.onFailed(responseHandler.handleException(exception), exception)
            }
        }
    }

    suspend fun listFollower(
        username: String?,
        callback: ResponseCallback<List<User>>
    ) {
        withContext(Dispatchers.IO) {
            val follower = api.listFollower(username)
            try {
                if (follower.isSuccessful) {
                    callback.onComplete(responseHandler.handleSuccess(follower.body()!!))
                } else {
                    callback.onErrorResponse(responseHandler.handleError(follower.errorBody()!!))
                }
            } catch (exception: Exception) {
                callback.onFailed(responseHandler.handleException(exception), exception)
            }
        }
    }

    suspend fun listFollowing(
        username: String?,
        callback: ResponseCallback<List<User>>
    ) {
        withContext(Dispatchers.IO) {
            val following = api.listFollowing(username)
            try {
                if (following.isSuccessful) {
                    callback.onComplete(responseHandler.handleSuccess(following.body()!!))
                } else {
                    callback.onErrorResponse(responseHandler.handleError(following.errorBody()!!))
                }
            } catch (exception: Exception) {
                callback.onFailed(responseHandler.handleException(exception), exception)
            }
        }
    }

    interface ResponseCallback<Response> {
        fun onComplete(response: Resource<Response>)
        fun onErrorResponse(response: Resource<Response>)
        fun onFailed(response: Resource<Response>, exception: Exception)
    }
}