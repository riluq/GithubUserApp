package com.riluq.consumerapp.ui.main

import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.riluq.consumerapp.BR
import com.riluq.consumerapp.R
import com.riluq.consumerapp.ViewModelFactory
import com.riluq.consumerapp.databinding.ActivityMainBinding
import com.riluq.consumerapp.ui.base.BaseActivity
import com.riluq.consumerapp.utils.MappingHelper
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

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()

        adapter = FavoriteAdapter(
            FavoriteAdapter.OnClickListener { favoriteEntity, v -> // menu
                val menu = PopupMenu(v.context, v)
                menu.menuInflater.inflate(R.menu.favorite_menu, menu.menu)

                menu.setOnMenuItemClickListener { menuItem ->
                    if (menuItem.itemId == R.id.action_delete) {
                        MaterialDialog(this).show {
                            title(R.string.delete_title)
                            message(R.string.delete_message)
                            positiveButton(R.string.yes) {
                                getViewModel()?.deleteFavorite(favoriteEntity, adapter)
                            }
                            negativeButton(R.string.no) {
                                it.cancel()
                            }
                        }
                    }
                    false
                }
                menu.show()
            }
        )
        adapter.submitList(MappingHelper.mapCursorToArrayList(getViewModel()?.getFavorites()))
        adapter.notifyDataSetChanged()

        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter


    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(MappingHelper.mapCursorToArrayList(getViewModel()?.getFavorites()))
        adapter.notifyDataSetChanged()
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