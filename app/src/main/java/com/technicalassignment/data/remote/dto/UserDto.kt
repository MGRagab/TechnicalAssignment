package com.technicalassignment.data.remote.dto

import com.technicalassignment.domain.model.User

data class UserDto(
    val avatar: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val job: String? = ""
) {
    fun toUser(): User {
        return User(avatar, email, first_name, id, last_name, job)
    }
}