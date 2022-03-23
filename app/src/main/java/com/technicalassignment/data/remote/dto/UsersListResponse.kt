package com.technicalassignment.data.remote.dto

data class UsersListResponse(
    val data: List<UserDto>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)