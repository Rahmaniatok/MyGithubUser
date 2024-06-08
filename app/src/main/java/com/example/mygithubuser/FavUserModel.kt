package com.example.mygithubuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mygithubuser.database.FavUser
import com.example.mygithubuser.database.FavUserDao
import com.example.mygithubuser.database.FavUserDatabase
import kotlinx.coroutines.launch

class FavUserModel(application: Application) : AndroidViewModel(application) {

    private val favUserDao: FavUserDao = FavUserDatabase.getDatabase(application).favUserDao()

    fun getFavoriteUser(): LiveData<List<FavUser>> {
        return favUserDao.getFavoriteUser()
    }

    fun addFavoriteUser(id: String, name: String, avatarUrl: String) {
        viewModelScope.launch {
            try {
                val favUser = FavUser(id, name, avatarUrl)
                favUserDao.addFavoriteUser(favUser)
                Log.d("FavUserModel", "Favorite user added successfully with id: $id")
            } catch (e: Exception) {
                Log.e("FavUserModel", "Failed to add favorite user with id: $id", e)
            }
        }
    }

    fun deleteFavoriteUser(id: String) {
        viewModelScope.launch {
            try {
                favUserDao.deleteFavoriteUser(id)
                Log.d("FavUserModel", "Favorite user deleted successfully with id: $id")
            } catch (e: Exception) {
                Log.e("FavUserModel", "Failed to delete favorite user with id: $id", e)
            }
        }
    }

    fun isFavoriteUser(id: String): LiveData<Boolean> {
        return liveData {
            val isFavorite = favUserDao.checkUser(id) > 0
            emit(isFavorite)
        }
    }
}