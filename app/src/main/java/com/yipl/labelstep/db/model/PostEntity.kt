package com.yipl.labelstep.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "post")
data  class PostEntity(
        @ColumnInfo(name = "userId") var userId: Int,
        @PrimaryKey @ColumnInfo(name = "id") var id: Int,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "body") var body: String
        )

