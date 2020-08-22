package com.riluq.githubuserapp.ui.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.riluq.githubuserapp.R
import com.riluq.githubuserapp.ui.detail.followersfollowing.FollowersFollowingFragment

class DetailPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    username: String
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val TAB_TITLES = arrayOf(
        R.string.tab_followers,
        R.string.tab_following
    )

    private val fragments = listOf<Fragment>(
        FollowersFollowingFragment.newInstance(username, true), // Followers
        FollowersFollowingFragment.newInstance(username, false) // Following
    )

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(TAB_TITLES[position])

}