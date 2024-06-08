package com.example.mygithubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.databinding.FragmentFollowBinding
import com.example.mygithubuser.databinding.FragmentFollowersBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowersFragment : Fragment() {

    companion object {
        private const val ARG_LOGIN = "login"

        internal val TAG = FollowersFragment::class.java.simpleName


        fun newInstance(login: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_LOGIN, login)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentFollowersBinding
    private val listFollowers: ArrayList<Account> = ArrayList()
    private var login: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login = arguments?.getString(ARG_LOGIN)
        setupRecyclerFollow()
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.listFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.listFollowers.addItemDecoration(itemDecoration)
    }

    private fun setupRecyclerFollow() {
        binding.progressBar.visibility = View.VISIBLE

        val client = AsyncHttpClient()

<<<<<<< HEAD
=======
        client.addHeader("Authorization", "token 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")
>>>>>>> parent of 1ab16a7 (FavUser Update)
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/$login/followers"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                listFollowers.clear()

                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("login")
                        val avatarUrl = jsonObject.getString("avatar_url")
                        val account = Account(name, avatarUrl)
                        listFollowers.add(account)
                    }
                    val adapter = FollowAdapter(listFollowers)
                    binding.listFollowers.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
