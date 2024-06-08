package com.example.mygithubuser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.database.FavUser

class FavoriteListAdapter(private val context: Context, private var listFavUser: List<FavUser>) :
    RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageView: ImageView = itemView.findViewById(R.id.ivAvatar)
        private val textView: TextView = itemView.findViewById(R.id.tvItem)

        fun bind(favUser: FavUser){
            Glide.with(itemView.context)
                .load(favUser.avatarUrl) // URL Gambar
                .into(imageView) // imageView mana yang akan diterapkan
            textView.text = favUser.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFavUser = listFavUser[position]
        holder.bind(currentFavUser)
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(currentFavUser)
        }

    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(favUser: FavUser)
    }

    override fun getItemCount(): Int {
        return listFavUser.size
    }
}