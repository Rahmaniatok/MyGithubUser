package com.example.mygithubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavUserDao {
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addFavoriteUser(favUser: FavUser)

    @Query("SELECT * FROM fav_user")
    fun getFavoriteUser(): LiveData<List<FavUser>>

    @Query("SELECT count(*) FROM fav_user WHERE fav_user.id = :id")
    suspend fun checkUser(id: String): Int

    @Query("DELETE FROM fav_user WHERE fav_user.id = :id")
    suspend fun deleteFavoriteUser(id: String): Int
}