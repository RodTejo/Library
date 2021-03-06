package com.example.library.core.utils

import android.app.AlertDialog
import android.content.Context
import com.example.library.R
import javax.inject.Inject

class GenericUtility @Inject constructor() {
    fun showErrorMessage(message: String?, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok) { _, _ -> Unit}
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}