package com.riluq.consumerapp.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.riluq.consumerapp.R
import com.riluq.consumerapp.utils.NetworkUtils
import dagger.android.AndroidInjection


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    BaseFragment.Callback {

    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V?

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)

        performDataBinding()
    }

    fun checkInternetConnection(doSomething: () -> Unit, noInternetConnection: () -> Unit) {
        if (NetworkUtils.isInternetAvailable(this)) {
            doSomething()
        } else {
            noInternetConnection()
        }
    }

    fun showSnackBar(message: String) {
        hideKeyboard()
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackBarError(message: String) {
        hideKeyboard()
        val snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.errorColor))
        snackbar.show()
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getView().windowToken, 0)
    }

    private fun getView(): View {
        return findViewById(android.R.id.content)
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding?.lifecycleOwner = this
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    open fun getViewDataBinding(): T {
        return mViewDataBinding!!
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String?) {

    }
}