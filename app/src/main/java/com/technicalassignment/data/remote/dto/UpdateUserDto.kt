package com.technicalassignment.data.remote.dto

import com.technicalassignment.domain.model.UpdateUser

data class UpdateUserDto(
    val job: String? = ""
){
    fun toUpdateUser () : UpdateUser{
        return UpdateUser(job)
    }
}
