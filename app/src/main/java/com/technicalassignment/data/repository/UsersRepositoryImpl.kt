package com.technicalassignment.data.repository

import com.technicalassignment.data.remote.UsersApiService
import com.technicalassignment.data.remote.dto.UpdateUserDto
import com.technicalassignment.data.remote.dto.UsersListResponse
import com.technicalassignment.data.remote.request.UpdateUserRequest
import com.technicalassignment.domain.repository.UsersRepository
import io.reactivex.Single

class UsersRepositoryImpl(
    private val usersApiService: UsersApiService
) : UsersRepository {


    override fun getUsersList(page: Int): Single<UsersListResponse> {
        return usersApiService.getUsersList(page, 20)
    }

    override fun updateUser(id: Int, job: String): Single<UpdateUserDto> {
        return usersApiService.updateUser(id, UpdateUserRequest(job))
    }
}