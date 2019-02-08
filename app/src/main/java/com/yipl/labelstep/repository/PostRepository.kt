package com.yipl.labelstep.repository

import androidx.lifecycle.LiveData
import com.yipl.labelstep.api.ApiClient
import com.yipl.labelstep.db.LabelDatabase
import com.yipl.labelstep.db.model.Post
import com.yipl.labelstep.db.model.PostEntity
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository
@Inject constructor(private val apiClient: ApiClient, private val database: LabelDatabase) {


    suspend fun getPosts(): LiveData<ResultOrErrorWrapper<List<Post>>> {
        var posts = emptyList<PostEntity>()

        return object : NetworkBoundResource<List<Post>, List<PostEntity>>() {
            override suspend fun fetchDataFromDB(): LiveData<List<Post>> {
                return database.postDao().selectAll()
            }

            override fun isDataFromNWRequired(): Boolean {
                return true
            }

            override fun createNWCall(): Deferred<List<PostEntity>> {
                return apiClient.getPosts()
            }

            override suspend fun processDownloadedData(data: List<PostEntity>) {
                var count = 0

                val job = GlobalScope.launch {
                    posts.forEach {
                        database.postDao().insertPosts(it)

                        count++

                        if (count == 10) {
                            delay(10000)
                        }
                    }
                }

                job.join()
            }

        }.start().asLiveData()

//        var resultOrErrorWrapper: ResultOrErrorWrapper<List<PostEntity>>
//        try {
//            withContext(Dispatchers.IO) { posts = apiClient.getPosts().await() }
//            resultOrErrorWrapper =
//        } catch (e: Exception) {
//            resultOrErrorWrapper =
//        }

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

//        var count = 0
//
//        val job = GlobalScope.launch {
//            posts.forEach {
//                database.postDao().insertPosts(it)
//
//                count++
//
//                if (count == 10) {
//                    delay(10000)
//                }
//            }
//        }
//
//        job.join()
    }

    fun showPosts(): LiveData<List<Post>> = database.postDao().selectAll()
}