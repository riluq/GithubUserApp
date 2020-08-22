package com.riluq.githubuserapp.ui.detail.followersfollowing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.riluq.githubuserapp.BR
import com.riluq.githubuserapp.R
import com.riluq.githubuserapp.ViewModelFactory
import com.riluq.githubuserapp.databinding.FragmentFollowersFollowingBinding
import com.riluq.githubuserapp.ui.adapter.UserAdapter
import com.riluq.githubuserapp.ui.base.BaseFragment
import com.riluq.githubuserapp.ui.detail.DetailViewModel
import com.riluq.githubuserapp.vo.Status
import javax.inject.Inject


class FollowersFollowingFragment :
    BaseFragment<FragmentFollowersFollowingBinding, DetailViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: FragmentFollowersFollowingBinding

    private var mUsername: String? = null
    private var mIsFollowers: Boolean? = null

    private lateinit var adapter: UserAdapter

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_IS_FOLLOWERS = "is_followers"

        @JvmStatic
        fun newInstance(username: String, isFollowers: Boolean) =
            FollowersFollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                    putBoolean(ARG_IS_FOLLOWERS, isFollowers)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()

        arguments?.let {
            mUsername = it.getString(ARG_USERNAME)
            mIsFollowers = it.getBoolean(ARG_IS_FOLLOWERS)
        }

        adapter = UserAdapter(UserAdapter.OnClickListener {

        })



        initRecyclerView()

        mActivity?.checkInternetConnection(
            doSomething = {
                getData()
            },
            noInternetConnection = {
                mActivity?.showSnackBarError(resources.getString(R.string.no_internet))
                binding.swipeRefresh.isRefreshing = false
            }
        )

        initSwipeRefresh()
    }

    private fun getData() =
        if (mIsFollowers!!) getFollowers()
        else getFollowing()


    private fun initSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeResources(
            R.color.primaryColor,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        binding.swipeRefresh.setOnRefreshListener {
            mActivity?.checkInternetConnection(doSomething = {
                getData()
            }, noInternetConnection = {
                mActivity?.showSnackBarError(resources.getString(R.string.no_internet))
            })
        }

    }

    private fun getFollowers() {
        binding.swipeRefresh.isRefreshing = true
        getViewModel()?.followers(mUsername!!)?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.DONE -> {
                    adapter.submitList(it.data)
                    adapter.notifyDataSetChanged()
                    binding.swipeRefresh.isRefreshing = false
                }
                Status.ERROR -> {
                    mActivity?.showSnackBarError(it.errorResponse?.message ?: it.message!!)
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun getFollowing() {
        binding.swipeRefresh.isRefreshing = true
        getViewModel()?.following(mUsername!!)?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.DONE -> {
                    adapter.submitList(it.data)
                    adapter.notifyDataSetChanged()
                    binding.swipeRefresh.isRefreshing = false
                }
                Status.ERROR -> {
                    mActivity?.showSnackBarError(it.errorResponse?.message ?: it.message!!)
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvFollowersFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvFollowersFollowing.setHasFixedSize(true)
        binding.rvFollowersFollowing.adapter = adapter
    }

    private fun viewModel(): Lazy<DetailViewModel> = viewModels { factory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_followers_following

    override fun getViewModel(): DetailViewModel? = viewModel().value
}