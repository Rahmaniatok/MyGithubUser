package com.example.mygithubuser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygithubuser.database.FavUser
import com.example.mygithubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteListAdapter
    private lateinit var rvFavUser: RecyclerView
    private lateinit var favUserModel: FavUserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favUserModel = ViewModelProvider(this).get(FavUserModel::class.java)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvFavUser = binding.rvFavUser
        rvFavUser.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with an empty list
        adapter = FavoriteListAdapter(this, emptyList())
        rvFavUser.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteListAdapter.OnItemClickCallback {
            override fun onItemClicked(favUser: FavUser) {
                Log.d("FavoriteListAdapter", "Item clicked: ${favUser.id}, ${favUser.avatarUrl}")
                Intent(this@FavoriteUserActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_LOGIN, favUser.id)
                    startActivity(this)
                }
            }
        })

        // Observe the favorite users and update the adapter's list
        favUserModel.getFavoriteUser()?.observe(this, Observer { listUser ->
            listUser?.let {
                adapter = FavoriteListAdapter(this, it)
                rvFavUser.adapter = adapter
                adapter.setOnItemClickCallback(object : FavoriteListAdapter.OnItemClickCallback {
                    override fun onItemClicked(favUser: FavUser) {
                        Log.d("FavoriteListAdapter", "Item clicked: ${favUser.id}, ${favUser.avatarUrl}")
                        Intent(this@FavoriteUserActivity, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.EXTRA_LOGIN, favUser.id)
                            startActivity(this)
                        }
                    }
                })
            }
        })
    }
}