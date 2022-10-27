package com.ensibuuko.android_dev_coding_assigment.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class User {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("username")
    @Expose
    var username: String? = null
}

@Entity(tableName = "posts_table")
@Parcelize
data class Posts(
    @PrimaryKey val id: Int = 0,
    var title: String? = null,
    var body: String? = null
) : Parcelable

data class Comments(
    var id: Int = 0,
    var name: String? = null,
    var email: String? = null,
    var body: String? = null
)