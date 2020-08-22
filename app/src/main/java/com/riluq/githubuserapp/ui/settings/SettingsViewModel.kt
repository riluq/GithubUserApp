package com.riluq.githubuserapp.ui.settings

import com.riluq.githubuserapp.data.source.GithubUserRepository
import com.riluq.githubuserapp.ui.base.BaseViewModel

class SettingsViewModel(repository: GithubUserRepository) : BaseViewModel(repository) {

    fun setReminder(reminderState: Boolean) = repository.setReminder(reminderState)

}