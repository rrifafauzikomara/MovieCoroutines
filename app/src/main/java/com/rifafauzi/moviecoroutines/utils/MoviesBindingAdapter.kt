package com.rifafauzi.moviecoroutines.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rifafauzi.moviecoroutines.R

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (imgUrl.isNullOrEmpty()) {
        Glide.with(imgView.context)
            .load(R.drawable.ic_no_data)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_error))
            .into(imgView)
    } else {
        Glide.with(imgView.context)
            .load(LINK_IMAGE+imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_error))
            .into(imgView)
    }
}