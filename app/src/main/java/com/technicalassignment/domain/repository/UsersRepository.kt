package com.technicalassignment.domain.repository

import com.technicalassignment.data.remote.dto.UsersListResponse
import io.reactivex.Single

interface UsersRepository {
    fun getUsersList(page : Int) : Single<UsersListResponse>
}