package com.riluq.githubuserapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riluq.githubuserapp.R


@BindingAdapter("app:imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken)
            )
            .into(this)

    }
}

@BindingAdapter("app:textString")
fun TextView.bindTextString(text: String?) {
    text.let {
        this.text = it ?: "-"
    }
}