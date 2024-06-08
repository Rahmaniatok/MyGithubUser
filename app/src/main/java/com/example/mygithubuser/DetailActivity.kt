package com.example.mygithubuser

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.BuildConfig
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    companion object {
        private val TAG = DetailActivity::class.java.simpleName
        const val EXTRA_LOGIN = "extra_login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }


    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<FavUserModel>()

    private var name: String? = null
    private var favUserId: String? = null
    private var avatarUrl: String? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val keyLogin = intent.getStringExtra(EXTRA_LOGIN)
        keyLogin?.let {
            favUserId = it
            getDetail(it)
            tabSetup(it)
        }

        setupFavoriteButton()
    }

    private fun getDetail(keyLogin: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()

<<<<<<< HEAD
=======
        client.addHeader("Authorization", "token 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")
>>>>>>> parent of 1ab16a7 (FavUser Update)
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/$keyLogin"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                binding.progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val login = responseObject.getString("login")
                    name = responseObject.getString("name")
                    val followers = responseObject.getInt("followers")
                    val following = responseObject.getInt("following")
                    avatarUrl = responseObject.getString("avatar_url")

                    binding.tvLoginDetail.text = login
                    binding.tvNameDetail.text = name
                    binding.tvNumberFollower.text = followers.toString()
                    binding.tvNumberFollowing.text = following.toString()

                    Glide.with(this@DetailActivity)
                        .load(avatarUrl) // URL Gambar
                        .into(binding.ivAvatarDetail) // imageView mana yang akan diterapkan

                    checkFavoriteStatus(login)
                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@DetailActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkFavoriteStatus(userId: String) {
        viewModel.isFavoriteUser(userId).observe(this, Observer { isFavorite ->
            this.isFavorite = isFavorite
            updateFavoriteButton(isFavorite)
        })
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.ibFavoriteButton.setImageResource(R.drawable.favorite)
        } else {
            binding.ibFavoriteButton.setImageResource(R.drawable.not_favorite)
        }
    }

    private fun setupFavoriteButton() {
        binding.ibFavoriteButton.setOnClickListener {
            favUserId?.let { id ->
                if (isFavorite) {
                    viewModel.deleteFavoriteUser(id)
                    Toast.makeText(this, "Deleted from Favorite :(", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addFavoriteUser(id, name ?: "", avatarUrl ?: "")
                    Toast.makeText(this, "Added to Favorite! It's so cute :)", Toast.LENGTH_SHORT).show()
                }
                isFavorite = !isFavorite
                updateFavoriteButton(isFavorite)
            }
        }
    }

    private fun tabSetup(login: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, login)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }
}