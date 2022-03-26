package com.technicalassignment.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showNetworkError(view: View, networkException: NetworkException) {
    Snackbar.make(
        view,
        "${networkException.code}:: ${networkException.errorMessage}",
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Throwable.mapException(): NetworkException {
    return when (this) {
        is IOException -> NoNetworkException(this)
        is HttpException -> HttpCallFailureException(this.code(), this.message.toString(), this)
        else -> NetworkException(1103, "Unknown Exception", this)
    }

}