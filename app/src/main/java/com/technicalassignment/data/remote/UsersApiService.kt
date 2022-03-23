package com.technicalassignment.data.remote

import com.technicalassignment.data.remote.dto.UsersListResponse
import com.technicalassignment.utils.USERS_END_POINT
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApiService {
    @GET(USERS_END_POINT)
    fun getUsersList(@Query("page") page: Int , @Query("per_page") per_page : Int): Single<UsersListResponse>

}