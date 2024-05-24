package com.example.mygithubuser

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
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
            getDetail(it)
            tabSetup(it)
        }
    }
    private fun getDetail(keyLogin: String){
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token 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")
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
                    val name = responseObject.getString("name")
                    val followers = responseObject.getInt("followers")
                    val following = responseObject.getInt("following")
                    val avatar_url = responseObject.getString("avatar_url")

                    binding.tvLoginDetail.text = login
                    binding.tvNameDetail.text = name
                    binding.tvNumberFollower.text = followers.toString()
                    binding.tvNumberFollowing.text = following.toString()

                    Glide.with(this@DetailActivity)
                        .load(avatar_url) // URL Gambar
                        .into(binding.ivAvatarDetail) // imageView mana yang akan diterapkan

                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
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
    private fun tabSetup(login: String){

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