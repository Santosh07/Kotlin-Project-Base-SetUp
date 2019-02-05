package com.yipl.labelstep.ui.post

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.yipl.labelstep.db.model.Post

class PostViewModel : BaseObservable() {
    lateinit var post: Post
    @Bindable
    fun getTitle(): String {
        return post.title
    }

    @Bindable
    fun getBody():String{
        return post.body
    }
}