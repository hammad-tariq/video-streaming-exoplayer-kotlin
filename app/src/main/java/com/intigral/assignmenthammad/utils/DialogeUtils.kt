package com.intigral.assignmenthammad.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object DialogeUtils{

    fun showErrorDialog(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                .create().show()
    }

}
