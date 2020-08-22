package com.riluq.githubuserapp.ui.detail.followersfollowing

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FollowersFollowingFragmentProvider {
    @ContributesAndroidInjector
    abstract fun provideFollowersFollowingFragmentProvider(): FollowersFollowingFragment
}