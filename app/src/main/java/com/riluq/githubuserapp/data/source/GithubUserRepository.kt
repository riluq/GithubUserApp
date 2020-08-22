package com.riluq.githubuserapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.riluq.githubuserapp.data.source.local.LocalRepository
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity
import com.riluq.githubuserapp.data.source.remote.RemoteRepository
import com.riluq.githubuserapp.data.source.remote.response.DetailUserResponse
import com.riluq.githubuserapp.data.source.remote.response.SearchUserResponse
import com.riluq.githubuserapp.data.source.remote.response.User
import com.riluq.githubuserapp.vo.Resource
import javax.inject.Inject

class GithubUserRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : GithubUserDataSource {

    override suspend fun onSearch(username: String?): LiveData<Resource<SearchUserResponse>> {
        val search = MutableLiveData<Resource<SearchUserResponse>>()
        remoteRepository.searchUser(username,
            object : RemoteRepository.ResponseCallback<SearchUserResponse> {
                override fun onComplete(response: Resource<SearchUserResponse>) {
                    search.postValue(response)
                }

                override fun onFailed(
                    response: Resource<SearchUserResponse>,
                    exception: Exception
                ) {
                    search.postValue(response)
                }

                override fun onErrorResponse(response: Resource<SearchUserResponse>) {
                    search.postValue(response)
                }

            })
        return search
    }

    override suspend fun onGetDetail(username: String?): LiveData<Resource<DetailUserResponse>> {
        val detail = MutableLiveData<Resource<DetailUserResponse>>()
        remoteRepository.detailUser(username,
            object : RemoteRepository.ResponseCallback<DetailUserResponse> {
                override fun onComplete(response: Resource<DetailUserResponse>) {
                    detail.postValue(response)
                }

                override fun onFailed(
                    response: Resource<DetailUserResponse>,
                    exception: Exception
                ) {
                    detail.postValue(response)
                }

                override fun onErrorResponse(response: Resource<DetailUserResponse>) {
                    detail.postValue(response)
                }

            })
        return detail
    }

    override suspend fun onGetFollowers(username: String?): LiveData<Resource<List<User>>> {
        val followers = MutableLiveData<Resource<List<User>>>()
        remoteRepository.listFollower(username,
            object : RemoteRepository.ResponseCallback<List<User>> {
                override fun onComplete(response: Resource<List<User>>) {
                    followers.postValue(response)
                }

                override fun onFailed(response: Resource<List<User>>, exception: Exception) {
                    followers.postValue(response)
                }

                override fun onErrorResponse(response: Resource<List<User>>) {
                    followers.postValue(response)
                }

            })
        return followers
    }

    override suspend fun onGetFollowing(username: String?): LiveData<Resource<List<User>>> {
        val following = MutableLiveData<Resource<List<User>>>()
        remoteRepository.listFollowing(username,
            object : RemoteRepository.ResponseCallback<List<User>> {
                override fun onComplete(response: Resource<List<User>>) {
                    following.postValue(response)
                }

                override fun onFailed(response: Resource<List<User>>, exception: Exception) {
                    following.postValue(response)
                }

                override fun onErrorResponse(response: Resource<List<User>>) {
                    following.postValue(response)
                }

            })
        return following
    }

    override fun getAllFavorite(): LiveData<PagedList<FavoriteEntity>> =
        LivePagedListBuilder(localRepository.getAllFavorite(), 20).build()

    override fun getFavorite(username: String): LiveData<FavoriteEntity> =
        localRepository.getFavoriteByUsername(username)

    override suspend fun addFavorite(favoriteEntity: FavoriteEntity) =
        localRepository.insertFavorite(favoriteEntity)

    override suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) =
        localRepository.deleteFavorite(favoriteEntity)

    override fun getReminder(): Boolean = localRepository.preferences().getReminder()!!

    override fun setReminder(reminderState: Boolean) =
        localRepository.preferences().setReminder(reminderState)

}