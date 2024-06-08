package com.example.mygithubuser.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fav_user")
data class FavUser(
    @PrimaryKey
    val id: String,

    val name: String,

    val avatarUrl: String
) : Serializable