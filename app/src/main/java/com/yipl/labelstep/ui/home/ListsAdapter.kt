package com.yipl.labelstep.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yipl.labelstep.R
import com.yipl.labelstep.db.model.Post
import com.yipl.labelstep.databinding.LayoutItemUserBinding
import com.yipl.labelstep.ui.post.PostViewModel
import com.yipl.labelstep.util.databinding.BindableAdapter
import java.util.*

class ListsAdapter(var context: Context) : RecyclerView.Adapter<ListsAdapter.ListViewHolder>(), BindableAdapter<List<Post>> {

    private var data: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding: LayoutItemUserBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_item_user, parent, false)
        return ListViewHolder(binding)
    }

    override fun setData(data: List<Post>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(data[position])

    class ListViewHolder(binding: LayoutItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel: PostViewModel

        init {
            viewModel = PostViewModel()
            binding.viewModel = viewModel
        }

        fun bind(item: Post) {
            viewModel.post = item
            Log.e("ViewModel", item.title)
//            // TODO: Bind the data with View
//            setOnClickListener {
//                // TODO: Handle on click
//            }
        }
    }
}