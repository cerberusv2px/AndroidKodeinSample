package me.jorgecastillo.viper.common.extensions

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.load(url: String, @DrawableRes placeHolderRes: Int = 0) {
  Picasso.get()
      .load(url)
      .also {
        if (placeHolderRes != 0) it.placeholder(placeHolderRes)
      }
      .into(this)
}