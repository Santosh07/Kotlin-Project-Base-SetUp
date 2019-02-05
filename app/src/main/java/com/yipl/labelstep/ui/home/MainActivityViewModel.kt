package com.yipl.labelstep.ui.home

import com.yipl.labelstep.Repository.Repository
import com.yipl.labelstep.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val repository: Repository): BaseViewModel() {

    var posts = repository.showPosts()
        private set

    fun getPosts() {
        launch {
            repository.getPosts()
        }
    }
}
