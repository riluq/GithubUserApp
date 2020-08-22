package com.riluq.consumerapp.ui.base

import androidx.lifecycle.ViewModel
import com.riluq.consumerapp.data.source.GithubUserRepository

abstract class BaseViewModel(val repository: GithubUserRepository) : ViewModel() {

}