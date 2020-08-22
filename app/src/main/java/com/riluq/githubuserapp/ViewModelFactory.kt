package com.riluq.githubuserapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riluq.githubuserapp.data.source.GithubUserRepository
import com.riluq.githubuserapp.ui.detail.DetailViewModel
import com.riluq.githubuserapp.ui.main.MainViewModel
import com.riluq.githubuserapp.ui.main.favorite.FavoriteViewModel
import com.riluq.githubuserapp.ui.main.home.HomeViewModel
import com.riluq.githubuserapp.ui.settings.SettingsViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                githubUserRepository
            ) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                githubUserRepository
            ) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                githubUserRepository
            ) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(
                githubUserRepository
            ) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel(
                githubUserRepository
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}