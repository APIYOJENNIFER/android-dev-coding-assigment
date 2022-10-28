package com.ensibuuko.android_dev_coding_assigment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Posts::class], version = 1)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao

    class Callback @Inject constructor(
        private val database: Provider<PostsDatabase>,
        private val applicationScope:CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().postsDao()
            applicationScope.launch {
                dao.insert(Posts(1, "Beans Prices", "So high"))
                dao.insert(Posts(2, "Maize","Investigation"))
                dao.insert(Posts(3, "Rice","Unaffordable"))
            }
        }
    }
}