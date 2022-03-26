package com.technicalassignment.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val avatar: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val job: String? = ""
) : Parcelable