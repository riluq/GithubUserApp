package com.riluq.githubuserapp.di.builder

import com.riluq.githubuserapp.ui.detail.DetailActivity
import com.riluq.githubuserapp.ui.detail.followersfollowing.FollowersFollowingFragmentProvider
import com.riluq.githubuserapp.ui.main.MainActivity
import com.riluq.githubuserapp.ui.main.favorite.FavoriteFollowingProvider
import com.riluq.githubuserapp.ui.main.home.HomeFragmentProvider
import com.riluq.githubuserapp.ui.settings.SettingsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            HomeFragmentProvider::class,
            FavoriteFollowingProvider::class
        ]
    )
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            FollowersFollowingFragmentProvider::class
        ]
    )
    abstract fun bindDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun bindSettingsActivity(): SettingsActivity
}