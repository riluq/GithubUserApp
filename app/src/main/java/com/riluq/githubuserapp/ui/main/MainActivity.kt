package com.riluq.githubuserapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.riluq.githubuserapp.BR
import com.riluq.githubuserapp.R
import com.riluq.githubuserapp.ViewModelFactory
import com.riluq.githubuserapp.databinding.ActivityMainBinding
import com.riluq.githubuserapp.ui.base.BaseActivity
import com.riluq.githubuserapp.ui.main.favorite.FavoriteFragment
import com.riluq.githubuserapp.ui.main.home.HomeFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityMainBinding

    var fragment: Fragment? = null
    private val SELECTED_MENU = "selected_menu"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()

        binding.bottomNavigation.setOnNavigationItemSelectedListener(
            onNavigationItemSelectedListener
        )

        if (savedInstanceState != null) {
            savedInstanceState.getInt(SELECTED_MENU)
        } else {
            binding.bottomNavigation.selectedItemId = R.id.action_home
        }

    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    fragment = HomeFragment.newInstance()
                }
                R.id.action_favorite -> {
                    fragment = FavoriteFragment.newInstance()
                }

            }
            fragment.let {
                if (it != null) {
                    supportFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.content_main, it)
                        .commit()
                }
            }

            true
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_MENU, binding.bottomNavigation.selectedItemId)
    }

    override fun onBackPressed() {
        MaterialDialog(this).show {
            title(R.string.title_dialog_exit_app)
            message(R.string.message_dialog_exit_app)
            positiveButton(R.string.yes) {
                finishAffinity()
            }
            negativeButton(R.string.no) {
                it.cancel()
            }
        }
    }

    private fun viewModel(): Lazy<MainViewModel> = viewModels { factory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel? = viewModel().value

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}