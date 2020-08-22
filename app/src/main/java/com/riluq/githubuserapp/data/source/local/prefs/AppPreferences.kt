package com.riluq.githubuserapp.data.source.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class AppPreferences @Inject constructor(context: Context) {
    private val keyReminder = "key_reminder"
    private val sharedPreferencesName = "app_preferences"

    private var mPrefs: SharedPreferences? = null

    init {
        mPrefs = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
    }

    fun setReminder(reminderState: Boolean) {
        mPrefs?.edit {
            putBoolean(keyReminder, reminderState)
        }
    }

    fun getReminder(): Boolean? {
        return mPrefs?.getBoolean(keyReminder, false)
    }
}