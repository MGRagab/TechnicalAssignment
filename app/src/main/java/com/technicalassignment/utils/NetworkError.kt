package com.technicalassignment.utils


open class NetworkException(val code: Int, val errorMessage: String, error: Throwable?) : RuntimeException(error)

class NoNetworkException(error: Throwable) : NetworkException(1101, "No Internet Connection", error)

class HttpCallFailureException(code: Int, message: String, error: Throwable) :
    NetworkException(code, message, error)
