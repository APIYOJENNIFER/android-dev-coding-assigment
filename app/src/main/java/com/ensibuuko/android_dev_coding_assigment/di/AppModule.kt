package com.ensibuuko.android_dev_coding_assigment.di

import android.app.Application
import androidx.room.Room
import com.ensibuuko.android_dev_coding_assigment.data.PostsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: PostsDatabase.Callback
    ) = Room.databaseBuilder(app, PostsDatabase::class.java, "posts_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()


    @Provides
    fun providePostsDao(db: PostsDatabase) = db.postsDao()

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}