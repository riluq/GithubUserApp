package com.riluq.consumerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riluq.consumerapp.data.source.GithubUserRepository
import com.riluq.consumerapp.ui.main.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                githubUserRepository
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}