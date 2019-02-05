package com.yipl.labelstep.api
import com.yipl.labelstep.db.model.PostEntity
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    fun getPosts(): Deferred<List<PostEntity>>

}