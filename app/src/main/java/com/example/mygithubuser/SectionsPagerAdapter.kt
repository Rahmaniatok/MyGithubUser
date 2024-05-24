package com.example.mygithubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowFragment.newInstance(login)
            1 -> FollowersFragment.newInstance(login)
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int {
        return 2 // Number of tabs
    }
}