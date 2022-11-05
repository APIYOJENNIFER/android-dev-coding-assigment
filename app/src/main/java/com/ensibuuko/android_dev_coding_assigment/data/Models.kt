package com.ensibuuko.android_dev_coding_assigment.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class User(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)

@Entity(tableName = "posts_table")
@Parcelize
data class Posts(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int = 0,
    val title: String,
    val body: String
) : Parcelable

@Entity(tableName = "comments_table")
data class Comments(
    @PrimaryKey val id: Int = 0,
    val postId: Int = 0,
    val name: String,
    val email: String,
    val body: String
)

@Entity(tableName = "wallet_table")
@Parcelize
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int = 0,
    val amount: String
) : Parcelable