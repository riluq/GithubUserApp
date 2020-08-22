package com.riluq.githubuserapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.riluq.githubuserapp.BR
import com.riluq.githubuserapp.R
import com.riluq.githubuserapp.ViewModelFactory
import com.riluq.githubuserapp.data.source.local.entity.FavoriteEntity
import com.riluq.githubuserapp.data.source.remote.response.User
import com.riluq.githubuserapp.databinding.ActivityDetailBinding
import com.riluq.githubuserapp.ui.base.BaseActivity
import com.riluq.githubuserapp.ui.main.home.HomeFragment.Companion.EXTRA_DETAIL
import com.riluq.githubuserapp.utils.LayoutState
import com.riluq.githubuserapp.utils.gone
import com.riluq.githubuserapp.utils.visible
import com.riluq.githubuserapp.vo.Status
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityDetailBinding

    private var firstStateOfFavorite = true

    private var favoriteEntity: FavoriteEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()

        val user = intent.getParcelableExtra<User>(EXTRA_DETAIL)
        if (user != null) {
            getViewModel()?.setUsername(user.username ?: "")

            getViewModel()?.getFavoriteByName(user.username ?: "")?.observe(this, Observer {
                if (firstStateOfFavorite) {
                    firstStateOfFavorite = if (it != null) {
                        getViewModel()?.setFavoriteState(true)
                        false
                    } else {
                        getViewModel()?.setFavoriteState(false)
                        false
                    }
                }
                favoriteEntity = it
            })

            getViewModel()?.hasFavorite?.observe(this, Observer { hasFavorite ->
                if (hasFavorite) {
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite_border)
                }
            })

            binding.imgFavorite.setOnClickListener {
                if (!getViewModel()?.hasFavorite?.value!!) {
                    getViewModel()?.addProduct(
                        FavoriteEntity(
                            null,
                            user.username,
                            user.photo,
                            user.htmlUrl
                        )
                    )
                    getViewModel()?.setFavoriteState(true)
                    showSnackBar(resources.getString(R.string.save_to_favorite))
                } else {
                    if (favoriteEntity != null) {
                        getViewModel()?.deleteFavorite(favoriteEntity!!)
                    }
                    getViewModel()?.setFavoriteState(false)
                    showSnackBar(resources.getString(R.string.delete_from_favorite))
                }
            }
        }

        getViewModel()?.layoutState?.observe(this, Observer {
            if (it != null)
                when (it) {
                    LayoutState.ERROR -> {
                        binding.fmLoadingRefresh.visible()
                        binding.fmLoadingRefresh.alpha = 0.4f
                        binding.imgLoading.gone()
                        binding.imgRefresh.visible()
                    }
                    LayoutState.DONE -> {
                        binding.fmLoadingRefresh.gone()
                        binding.fmLoadingRefresh.alpha = 0.0f
                        binding.imgLoading.gone()
                        binding.imgRefresh.gone()
                    }
                    LayoutState.LOADING -> {
                        binding.fmLoadingRefresh.visible()
                        binding.fmLoadingRefresh.alpha = 0.4f
                        binding.imgLoading.visible()
                        binding.imgRefresh.gone()
                    }
                }
        })

        getViewModel()?.username?.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                onBackPressed()
            } else {
                setUp(it)
            }
        })
    }

    private fun setUp(username: String) {
        getDetail()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgRefresh.setOnClickListener {
            getDetail()
        }

        val tabsPagerAdapter = DetailPagerAdapter(this, supportFragmentManager, username)
        binding.vpDetail.adapter = tabsPagerAdapter
        binding.tabsDetail.setupWithViewPager(binding.vpDetail)
    }

    private fun getDetail() {
        checkInternetConnection(
            doSomething = {
                getViewModel()?.setLayoutState(LayoutState.LOADING)
                getViewModel()?.detail()?.observe(this, Observer {
                    when (it.status) {
                        Status.DONE -> {
                            val data = it.data
                            getViewModel()?.setPhoto(data?.photo)
                            getViewModel()?.setName(data?.name)
                            getViewModel()?.setEmail(data?.email)
                            getViewModel()?.setCompany(data?.company)
                            getViewModel()?.setLocation(data?.location)

                            getViewModel()?.setLayoutState(LayoutState.DONE)
                        }
                        Status.ERROR -> {
                            getViewModel()?.setLayoutState(LayoutState.ERROR)
                            showSnackBarError(it.errorResponse?.message ?: it.message!!)
                        }
                    }
                })
            },
            noInternetConnection = {
                getViewModel()?.setLayoutState(LayoutState.ERROR)
                showSnackBarError(resources.getString(R.string.no_internet))
            }
        )
    }

    private fun viewModel(): Lazy<DetailViewModel> = viewModels { factory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_detail

    override fun getViewModel(): DetailViewModel? = viewModel().value

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}