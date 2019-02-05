package com.yipl.labelstep.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.yipl.labelstep.api.ApiService
import com.yipl.labelstep.db.dao.PostDao
import com.yipl.labelstep.db.model.Post
import com.yipl.labelstep.db.model.PostEntity
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository
@Inject constructor(private val apiService: ApiService, private val dao: PostDao) {


    suspend fun getPosts() = coroutineScope {
        var posts = emptyList<PostEntity>()


        try {
            withContext(Dispatchers.IO) { posts = apiService.getPosts().await() }
        } catch (e: Exception) {
            Log.i("Exception", "Message = ${e.message}")
        }

//        var i = 0
//
//        posts.forEach {
//            dao.insertPosts(it)
//
//            i++
//
//            if (i == 10) {
//                delay(10000)
//            }
//        }

        var count = 0

        val job = GlobalScope.launch {
            posts.forEach {
                dao.insertPosts(it)

                count++

                if (count == 10) {
                    delay(10000)
                }
            }
        }

        job.join()
    }

    fun showPosts(): LiveData<List<Post>> = dao.selectAll()
}