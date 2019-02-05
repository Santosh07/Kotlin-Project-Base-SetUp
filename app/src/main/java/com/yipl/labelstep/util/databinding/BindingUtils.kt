package com.yipl.labelstep.util.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("data")
fun <T> recyclerViewData(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        if (data != null) {
            (recyclerView.adapter as BindableAdapter<T>).setData(data)
        }
    }
}