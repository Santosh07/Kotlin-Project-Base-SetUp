package com.yipl.labelstep.repository

import com.yipl.labelstep.api.ApiClient
import com.yipl.labelstep.db.LabelDatabase
import com.yipl.labelstep.db.model.Post
import com.yipl.labelstep.db.model.PostEntity
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class PostRepository
@Inject constructor(private val apiClient: ApiClient, private val database: LabelDatabase) {

    suspend fun getPosts() = coroutineScope {
        object: NetworkBoundResource<List<Post>, List<PostEntity>>() {
            override val coroutineContext: CoroutineContext
                    get() = this@coroutineScope.coroutineContext

            suspend override fun fetchDataFromDB() = database.postDao().selectAll()

            override fun isDataFromNWRequired(dbDataModel: List<Post>): Boolean {
                return dbDataModel.isNullOrEmpty()
            }

//            override fun isDataFromNWRequired(): Boolean {
//                return true
//            }

            override fun createNWCall(): Deferred<List<PostEntity>> {
              return apiClient.getPosts()
            }

            override suspend fun processDownloadedData(data: List<PostEntity>) = coroutineScope {
                var count = 0

                //launch {
                    data.forEach {
                        database.postDao().insertPosts(it)

                        count++

                        if (count == 10) {
                            results.send(ResultOrErrorWrapper.error("Delaying", null))
                            delay(10000)
                            results.send(ResultOrErrorWrapper.error("Delay complete", null))
                        }
                    }

                    val dataFromDB = async { database.postDao().selectAll() }.await()

                    results.send(ResultOrErrorWrapper.success(dataFromDB))
                //}

                //job.join()
            }
          }
    }
}