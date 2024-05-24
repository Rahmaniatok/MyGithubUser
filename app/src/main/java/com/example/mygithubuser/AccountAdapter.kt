package com.example.mygithubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AccountAdapter(private val listAccount: ArrayList<Account>) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_account, viewGroup, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (name, avatarUrl) = listAccount[position]
        viewHolder.tvItem.text = name
        Glide.with(viewHolder.itemView.context)
            .load(avatarUrl) // URL Gambar
            .into(viewHolder.ivAvatar) // imageView mana yang akan diterapkan
        viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_LOGIN, listAccount[viewHolder.adapterPosition].name)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }
    override fun getItemCount(): Int {
        return listAccount.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar) // Add ivAvatar to ViewHolder
    }
}