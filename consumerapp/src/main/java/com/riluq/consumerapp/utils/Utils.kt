package com.riluq.consumerapp.utils

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


enum class LayoutState {
    LOADING,
    ERROR,
    DONE
}