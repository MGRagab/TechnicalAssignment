package com.technicalassignment.data.remote

import com.technicalassignment.data.remote.dto.UpdateUserDto
import com.technicalassignment.data.remote.dto.UserDto
import com.technicalassignment.data.remote.dto.UsersListResponse
import com.technicalassignment.data.remote.request.UpdateUserRequest
import com.technicalassignment.utils.HTTP_404_END_POINT
import com.technicalassignment.utils.USERS_END_POINT
import io.reactivex.Single
import retrofit2.http.*

interface UsersApiService {
    @GET(USERS_END_POINT)
    fun getUsersList(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Single<UsersListResponse>

    @PUT("$USERS_END_POINT/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Body updateUserRequest: UpdateUserRequest
    ): Single<UpdateUserDto>

}