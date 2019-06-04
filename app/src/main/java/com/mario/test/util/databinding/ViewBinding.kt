package com.mario.test.util.databinding

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.View.*
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mario.test.util.primitives.orFalse

/**
 * @param value "android:visibility" based on boolean value
 */
@BindingAdapter("android:visibility")
fun setViewVisibility(view: View, value: Boolean?) {
    view.visibility = if (value.orFalse()) VISIBLE else GONE
}

/**
 * @param value "android:visibility" based on boolean value
 */
@BindingAdapter("invisibility")
fun setViewInvisibility(view: View, value: Boolean?) {
    view.visibility = if (value.orFalse()) INVISIBLE else VISIBLE
}

@BindingAdapter("app:isRefreshing")
fun showLoadingSwipeRefreshLayout(srl: SwipeRefreshLayout, value: Boolean) {
    srl.isRefreshing = value
}

@BindingAdapter("app:textHtml")
fun setTextHtml(textView: TextView, stringRes: String) {
    textView.text = fromHtmlCompat(stringRes)
}

@Suppress("DEPRECATION")
fun fromHtmlCompat(source: String, @SuppressLint("InlinedApi") mode: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
    } else {
        return Html.fromHtml(source)
    }
}