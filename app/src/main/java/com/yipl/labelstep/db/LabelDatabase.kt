package com.yipl.labelstep.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yipl.labelstep.db.dao.PostDao
import com.yipl.labelstep.db.model.PostEntity

@Database(entities = arrayOf(PostEntity::class),version = 1, exportSchema = false)
abstract class LabelDatabase: RoomDatabase(){
    abstract fun daoAccess() : PostDao
}