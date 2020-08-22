package com.riluq.githubuserapp.ui.main.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.riluq.githubuserapp.BR
import com.riluq.githubuserapp.R
import com.riluq.githubuserapp.ViewModelFactory
import com.riluq.githubuserapp.databinding.FragmentHomeBinding
import com.riluq.githubuserapp.ui.adapter.UserAdapter
import com.riluq.githubuserapp.ui.base.BaseFragment
import com.riluq.githubuserapp.ui.detail.DetailActivity
import com.riluq.githubuserapp.ui.settings.SettingsActivity
import com.riluq.githubuserapp.utils.gone
import com.riluq.githubuserapp.utils.visible
import com.riluq.githubuserapp.vo.Status
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: UserAdapter

    companion object {
        const val EXTRA_DETAIL = "extra_detail"

        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()

        mActivity?.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        adapter = UserAdapter(
            UserAdapter.OnClickListener {
                val intent = Intent(mActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, it)
                startActivity(intent)
            }
        )

        initRecyclerView()

        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeResources(
            R.color.primaryColor,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        binding.swipeRefresh.setOnRefreshListener {
            mActivity?.checkInternetConnection(doSomething = {
                if (!getViewModel()?.searchQuery?.value.isNullOrEmpty()) {
                    search()
                } else {
                    mActivity?.showSnackBarError(resources.getString(R.string.please_submit_keyword))
                    binding.swipeRefresh.isRefreshing = false
                }
            }, noInternetConnection = {
                mActivity?.showSnackBarError(resources.getString(R.string.no_internet))
                binding.swipeRefresh.isRefreshing = false
            })
        }

        getViewModel()?.searchQuery?.observe(viewLifecycleOwner, Observer { query ->
            if (!query.isNullOrEmpty()) {
                mActivity?.checkInternetConnection(
                    doSomething = {
                        search()
                    },
                    noInternetConnection = {
                        mActivity?.showSnackBarError(resources.getString(R.string.no_internet))
                    }
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)

        val searchManager = mActivity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(mActivity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getViewModel()?.setSearhQuery(query)
                mActivity?.hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            val intent = Intent(mActivity, SettingsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun search() {
        binding.swipeRefresh.isRefreshing = true
        binding.swipeRefresh.visible()
        binding.tvFirstMessage.gone()
        getViewModel()?.searchUsername()?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.DONE -> {
                    adapter.submitList(it.data?.data)
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
        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.adapter = adapter
    }

    private fun viewModel(): Lazy<HomeViewModel> = viewModels { factory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getViewModel(): HomeViewModel? = viewModel().value
}