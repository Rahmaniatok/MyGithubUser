package com.example.mygithubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FollowAdapter(private val listFollow: ArrayList<Account>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_follow, viewGroup, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (name, avatarUrl) = listFollow[position]
        viewHolder.tvItemFollow.text = name
        Glide.with(viewHolder.itemView.context)
            .load(avatarUrl) // URL Gambar
            .into(viewHolder.ivAvatarFollow) // imageView mana yang akan diterapkan
    }
    override fun getItemCount(): Int {
        return listFollow.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemFollow: TextView = view.findViewById(R.id.tvItemFollow)
        val ivAvatarFollow: ImageView = view.findViewById(R.id.ivAvatarFollow) // Add ivAvatar to ViewHolder
    }
}