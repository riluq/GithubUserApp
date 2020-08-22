package com.riluq.githubuserapp.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.riluq.githubuserapp.BR
import com.riluq.githubuserapp.R
import com.riluq.githubuserapp.ViewModelFactory
import com.riluq.githubuserapp.databinding.SettingsActivityBinding
import com.riluq.githubuserapp.ui.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SettingsActivity : BaseActivity<SettingsActivityBinding, SettingsViewModel>(),
    HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private var mReminder: SwitchPreference? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            mReminder = preferenceManager.findPreference("reminder")

            mReminder?.setOnPreferenceChangeListener { preference, newValue ->
                (activity as SettingsActivity).getViewModel()
                    ?.setReminder(mReminder?.isChecked!!.not())

                if (mReminder?.isChecked?.not()!!)
                    (activity as SettingsActivity).showSnackBar(resources.getString(R.string.reminder_on_message))
                else
                    (activity as SettingsActivity).showSnackBar(resources.getString(R.string.reminder_off_message))

                // uncomment for check value of reminder
                //(activity as SettingsActivity).showSnackBar(mReminder?.isChecked?.not().toString())

                true
            }
        }
    }

    private fun viewModel(): Lazy<SettingsViewModel> = viewModels { factory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.settings_activity

    override fun getViewModel(): SettingsViewModel? = viewModel().value

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}