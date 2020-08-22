package com.riluq.githubuserapp.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.riluq.githubuserapp.BR
import com.riluq.githubuserapp.R
import com.riluq.githubuserapp.ViewModelFactory
import com.riluq.githubuserapp.data.source.remote.response.User
import com.riluq.githubuserapp.databinding.FragmentFavoriteBinding
import com.riluq.githubuserapp.ui.base.BaseFragment
import com.riluq.githubuserapp.ui.detail.DetailActivity
import com.riluq.githubuserapp.ui.main.home.HomeFragment
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: FragmentFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()

        mActivity?.setSupportActionBar(binding.toolbar)

        adapter = FavoriteAdapter(
            FavoriteAdapter.OnClickListener { favoriteEntity, v -> // menu
                val menu = PopupMenu(v.context, v)
                menu.menuInflater.inflate(R.menu.favorite_menu, menu.menu)

                menu.setOnMenuItemClickListener { menuItem ->
                    if (menuItem.itemId == R.id.action_delete) {
                        MaterialDialog(context!!).show {
                            title(R.string.delete_title)
                            message(R.string.delete_message)
                            positiveButton(R.string.yes) {
                                getViewModel()?.deleteFavorite(favoriteEntity)
                            }
                            negativeButton(R.string.no) {
                                it.cancel()
                            }
                        }
                    }
                    false
                }
                menu.show()
            },
            FavoriteAdapter.OnClickListener { favoriteEntity, _ -> // itemClick
                val intent = Intent(mActivity, DetailActivity::class.java)
                intent.putExtra(
                    HomeFragment.EXTRA_DETAIL,
                    User(
                        favoriteEntity.id.toString(),
                        favoriteEntity.username,
                        favoriteEntity.photo,
                        favoriteEntity.htmlUrl
                    )
                )
                startActivity(intent)
            }
        )

        getViewModel()?.getFavorites()?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter
    }

    private fun viewModel(): Lazy<FavoriteViewModel> = viewModels { factory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_favorite

    override fun getViewModel(): FavoriteViewModel? = viewModel().value
}