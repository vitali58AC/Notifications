package com.example.notifications.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.example.notifications.R

object LoadImageWithGlide {

    fun loadBitmapWithGlide(context: Context, url: String?): Bitmap? {
        val defaultImage = BitmapFactory.decodeResource(context.resources, R.drawable.default_promo)
        val urlImage = Glide.with(context)
            .asBitmap()
            .load(url)
            .submit()
            .get()
        return urlImage ?: defaultImage
    }
}