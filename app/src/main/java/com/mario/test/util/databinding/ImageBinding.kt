package com.mario.test.util.databinding

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.mario.test.App.Companion.context
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["roundedAvatar", "roundedPlaceholder"], requireAll = false)
    fun setRoundedUrl(view: ImageView, src: String?, placeHolder: Drawable?) {
        if (src != null) {
            val requestOptions = RequestOptions().centerCrop()
            val builder = Glide.with(context)
                    .asBitmap()
                    .load(src)
            if (placeHolder != null)
                builder.placeholder(placeHolder)
            builder.into(object : BitmapImageViewTarget(view) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    view.setImageDrawable(circularBitmapDrawable)
                }
            })
        }
    }

    @SuppressLint("CheckResult")
    @JvmStatic
    @BindingAdapter(value = ["android:src", "base64", "placeholder", "centerCrop", "fitCenter"], requireAll = false)
    fun setImageUrl(view: ImageView, src: String?, base64: String?, placeHolder: Drawable?, centerCrop: Boolean, fitCenter: Boolean) {
        // part with base 64 is not tested because data is mocked
        if (!base64.isNullOrEmpty()) {
            doAsync {
                // potentially heavy operation, must be done in an async way
                val imageBytes = Base64.decode(base64, Base64.DEFAULT)
                uiThread {
                    val requestOptions = RequestOptions()
                            .placeholder(placeHolder)
                    val builder = Glide.with(view.context)
                            .asBitmap()
                            .load(imageBytes)
                    if (centerCrop) {
                        requestOptions.centerCrop()
                    }
                    if (fitCenter) {
                        requestOptions.fitCenter()
                    }
                    builder.apply(requestOptions)
                    builder.into(view)
                }
            }
        } else if (placeHolder != null) {
            val requestOptions = RequestOptions()
                    .placeholder(placeHolder)

            val builder = Glide.with(view.context)
                    .load(src)
            if (centerCrop) {
                requestOptions.centerCrop()
            }
            if (fitCenter) {
                requestOptions.fitCenter()
            }
            builder.apply(requestOptions)
            builder.into(view)
        } else if (src != null) {
            val requestOptions = RequestOptions()
                    .placeholder(placeHolder)
            val builder = Glide.with(view.context)
                    .load(src)
            if (centerCrop) {
                requestOptions.centerCrop()
            }
            if (fitCenter) {
                requestOptions.fitCenter()
            }
            builder.apply(requestOptions)
            builder.into(view)
        } else {
            view.setImageBitmap(null)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["imageB64", "placeholder", "centerCrop"], requireAll = false)
    fun setBase64(view: ImageView, imageB64: String?, placeHolder: Drawable, centerCrop: Boolean) {
        // part with base 64 is not tested because data is mocked
        if (!imageB64.isNullOrEmpty()) {
            //using bytes with glide loses information about the colour and this solution can't be use for recycler view
            //it will glitch
            view.setImageBitmap(null)
            doAsync {
                val imageBytes = Base64.decode(imageB64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                uiThread {
                    if (centerCrop) {
                        view.scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                    view.setImageBitmap(bitmap)
                }
            }
        } else {
            view.setImageBitmap(null)
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imaveView: ImageView?, drawableRes: Int?) {
        drawableRes?.let { imaveView?.setImageResource(it) }
    }

}