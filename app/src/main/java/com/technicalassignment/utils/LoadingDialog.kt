package com.technicalassignment.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.technicalassignment.R


class LoadingDialog(private val activity: Activity) {

    private lateinit var dialog: AlertDialog
    fun startLoadingDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismissLoadingDialog() {
        dialog.dismiss()
    }
}