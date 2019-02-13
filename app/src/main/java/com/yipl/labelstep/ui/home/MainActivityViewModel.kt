package com.yipl.labelstep.ui.home

import androidx.lifecycle.MutableLiveData
import com.yipl.labelstep.db.model.Post
import com.yipl.labelstep.repository.PostRepository
import com.yipl.labelstep.ui.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val postRepository: PostRepository): BaseViewModel() {

    val posts = MutableLiveData<List<Post>>()

    val uiState = MutableLiveData<UIState>()

    fun getPosts() {
        launch {
            uiState.postValue(UIState.LOADING)

            val resultChannel = postRepository.getPosts()

            async {
                resultChannel.start()
            }

            for (result in resultChannel.results ) {
                if (result.data == null) {
                    errorMessage.postValue(result.message)
                    uiState.postValue(UIState.ERROR)
                } else {
                    uiState.postValue(UIState.SUCCESS)
                    posts.postValue(result.data)
                }
            }
        }
    }
}
