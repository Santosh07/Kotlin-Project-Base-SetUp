package com.yipl.labelstep.ui.home

import androidx.lifecycle.MutableLiveData
import com.yipl.labelstep.repository.PostRepository
import com.yipl.labelstep.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val postRepository: PostRepository): BaseViewModel() {

    val posts = postRepository.showPosts()

    val uiState = MutableLiveData<UIState>()

    fun getPosts() {
        launch {
            uiState.postValue(UIState.LOADING)
            val result = postRepository.getPosts()

            if (result.value?.data == null) {
                errorMessage.postValue(result.value?.message)
                uiState.postValue(UIState.ERROR)
            } else {
                uiState.postValue(UIState.SUCCESS)
            }
        }
    }
}
