package com.ensibuuko.android_dev_coding_assigment.di

import android.app.Application
import androidx.room.Room
import com.ensibuuko.android_dev_coding_assigment.data.PostsDatabase
import com.ensibuuko.android_dev_coding_assigment.network.CommentsApi
import com.ensibuuko.android_dev_coding_assigment.network.PostApi
import com.ensibuuko.android_dev_coding_assigment.network.UserApi
import com.ensibuuko.android_dev_coding_assigment.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ): PostsDatabase = Room.databaseBuilder(app, PostsDatabase::class.java, "posts_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providePostsDao(db: PostsDatabase) = db.postsDao()

    @Provides
    fun provideCommentsDao(db:PostsDatabase) = db.commentsDao()

    @Provides
    fun provideWalletDao(db: PostsDatabase) = db.walletDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePostsApi(retrofit: Retrofit): PostApi =
        retrofit.create(PostApi::class.java)

    @Provides
    @Singleton
    fun provideCommentsApi(retrofit: Retrofit): CommentsApi =
        retrofit.create(CommentsApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)
}