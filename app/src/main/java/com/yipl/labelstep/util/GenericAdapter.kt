package com.aabumu.genericadapter.usingbinding

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
 * Created by umesh on 8/10/18.
 */

class GenericAdapter<ITEM, BINDING : ViewDataBinding>(
        var itemLayout: Int,
        private var bindItem: BINDING.(ITEM) -> Unit,
        private val onViewHolderCreated: BINDING.() -> Unit = { })
    : RecyclerView.Adapter<GenericAdapter.GenericViewHolder<BINDING>>() {

    var itemList: List<ITEM>

    init {
        itemList = arrayListOf()
    }

    var TAG = GenericAdapter::class.java.simpleName
    fun setItem(itemList: List<ITEM>) {
        Log.e(TAG, "binding setItem === ${itemList.size}")
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<BINDING> {
        val binding = DataBindingUtil.inflate<BINDING>(LayoutInflater.from(parent.context), itemLayout, parent, false)
        val holder = GenericViewHolder<BINDING>(binding)

        binding.onViewHolderCreated()
        return holder
    }

    override fun onBindViewHolder(holder: GenericViewHolder< BINDING>, position: Int) {
        holder.binding.bindItem(itemList[position])
    }

    class GenericViewHolder< BINDING : ViewDataBinding>(var binding: BINDING) : RecyclerView.ViewHolder(binding.root) {

    }
}

// extension function for recyclerview
fun <ITEM, BINDING : ViewDataBinding> RecyclerView.setUpBinding(
        layout: Int,
        bindItem: BINDING.(ITEM) -> Unit,
        handleEvents: BINDING.() -> Unit = {},
        manager: LinearLayoutManager = LinearLayoutManager(this.context))
    : GenericAdapter<ITEM, BINDING>
{
    return GenericAdapter(layout, bindItem, handleEvents)
            .apply {
                layoutManager = manager
                adapter = this
            }
}