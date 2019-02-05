package com.yipl.labelstep.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yipl.labelstep.db.model.Post
import com.yipl.labelstep.db.model.PostEntity

@Dao
interface PostDao{
    @Query("SELECT * from post")
    fun selectAll() : LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(post: PostEntity)

}