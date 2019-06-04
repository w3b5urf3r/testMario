package com.mario.test.base

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T, VB>(private val viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root.apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }) {

    @Suppress("UNCHECKED_CAST")
    fun bindViewHolder(item: T, position: Int) {
        bindViewHolder(item, viewBinding as VB, position)
    }

    protected abstract fun bindViewHolder(item: T, binding: VB, position: Int)
}