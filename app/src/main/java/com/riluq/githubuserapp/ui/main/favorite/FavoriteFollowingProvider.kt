package com.riluq.githubuserapp.ui.main.favorite

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoriteFollowingProvider {
    @ContributesAndroidInjector
    abstract fun provideFavoriteFollowingProvider(): FavoriteFragment
}