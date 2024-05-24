package com.example.mygithubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    val name: String,
    val avatarUrl: String
) : Parcelable
