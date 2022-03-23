package com.technicalassignment.data.repository

import com.technicalassignment.data.remote.UsersApiService
import com.technicalassignment.data.remote.dto.UsersListResponse
import com.technicalassignment.domain.repository.UsersRepository
import io.reactivex.Single

class UsersRepositoryImpl(
    private val usersApiService: UsersApiService
) : UsersRepository {
    override fun getUsersList(page: Int): Single<UsersListResponse> {
        return usersApiService.getUsersList(page, 20)
    }
}