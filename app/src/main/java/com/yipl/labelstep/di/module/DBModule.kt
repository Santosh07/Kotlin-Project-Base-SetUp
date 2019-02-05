package com.yipl.labelstep.di.module

import android.app.Application
import androidx.room.Room
import com.yipl.labelstep.db.LabelDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    val DATABASE_NAME = "label_database"

    @Provides
    @Singleton
    fun provideDatabase(application: Application): LabelDatabase {
        return Room
               .databaseBuilder(
                   application.applicationContext,
                   LabelDatabase::class.java, DATABASE_NAME
               )
               .fallbackToDestructiveMigration()
               .allowMainThreadQueries()
               .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: LabelDatabase) = database.daoAccess()
}