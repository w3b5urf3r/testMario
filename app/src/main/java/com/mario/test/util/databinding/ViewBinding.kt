package com.mario.test.util.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:numbers", requireAll = false)
fun setNumbers(tv: TextView, numbers: List<Int>?) {
    tv.text = numbers?.joinToString { number: Int -> number.toString() }
}