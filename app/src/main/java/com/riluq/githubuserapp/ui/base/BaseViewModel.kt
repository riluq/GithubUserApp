package com.riluq.githubuserapp.ui.base

import androidx.lifecycle.ViewModel
import com.riluq.githubuserapp.data.source.GithubUserRepository

abstract class BaseViewModel(val repository: GithubUserRepository) : ViewModel() {
    fun getReminder() = repository.getReminder()
}